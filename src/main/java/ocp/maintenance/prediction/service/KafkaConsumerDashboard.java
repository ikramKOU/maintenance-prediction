// package ocp.maintenance.prediction.service;

// import com.fasterxml.jackson.databind.JsonNode;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import org.springframework.kafka.annotation.KafkaListener;
// import org.springframework.messaging.simp.SimpMessagingTemplate;
// import org.springframework.stereotype.Service;

// @Service
// public class KafkaConsumerDashboard {

//     private final SimpMessagingTemplate messagingTemplate;
//     private final ObjectMapper objectMapper = new ObjectMapper();
//     private static final String DASHBOARD_TOPIC = "/topic/dashboard-data";

//     public KafkaConsumerDashboard(SimpMessagingTemplate messagingTemplate) {
//         this.messagingTemplate = messagingTemplate;
//     }

//     @KafkaListener(
//         topics = "sensor-data",
//         groupId = "dashboard-group",
//         properties = {
//             "auto.offset.reset=earliest"
//         }
//     )
//     public void consumeSensorData(String sensorData) {
//         try {
//             DashboardData data = transformToDashboardFormat(sensorData);
//             messagingTemplate.convertAndSend(DASHBOARD_TOPIC, data);
//         } catch (Exception e) {
//             System.err.println("❌ Erreur lors du traitement des données Kafka : " + e.getMessage());
//         }
//     }

//     private DashboardData transformToDashboardFormat(String rawData) throws Exception {
//         JsonNode root = objectMapper.readTree(rawData);

//         int windowIndex = root.path("window_index").asInt();
//         JsonNode windowNode = root.path("window");

//         // On prend la première ligne (un tableau de capteurs)
//         JsonNode firstRow = windowNode.get(0);

//         double[] values = new double[firstRow.size()];
//         for (int i = 0; i < firstRow.size(); i++) {
//             values[i] = firstRow.get(i).asDouble();
//         }

//         return new DashboardData(windowIndex, values, System.currentTimeMillis());
//     }

//     // ✅ Classe DTO interne pour l’envoi au frontend via WebSocket
//     private static class DashboardData {
//         private final int windowIndex;
//         private final double[] values;
//         private final long timestamp;

//         public DashboardData(int windowIndex, double[] values, long timestamp) {
//             this.windowIndex = windowIndex;
//             this.values = values;
//             this.timestamp = timestamp;
//         }

//         public int getWindowIndex() {
//             return windowIndex;
//         }

//         public double[] getValues() {
//             return values;
//         }

//         public long getTimestamp() {
//             return timestamp;
//         }
//     }
// }
// package ocp.maintenance.prediction.service;

// import org.springframework.kafka.annotation.KafkaListener;
// import org.springframework.messaging.simp.SimpMessagingTemplate;
// import org.springframework.stereotype.Service;

// import com.fasterxml.jackson.annotation.JsonProperty;
// import com.fasterxml.jackson.databind.JsonNode;
// import com.fasterxml.jackson.databind.ObjectMapper;

// @Service
// public class KafkaConsumerDashboard {

//     private final SimpMessagingTemplate messagingTemplate;
//     private final ObjectMapper objectMapper = new ObjectMapper();
//     private static final String DASHBOARD_TOPIC = "/topic/dashboard-data";

//     public KafkaConsumerDashboard(SimpMessagingTemplate messagingTemplate) {
//         this.messagingTemplate = messagingTemplate;
//     }

//     @KafkaListener(
//         topics = {"sensor-data"},
//         groupId = "dashboard-group",
//         properties = {
//             "auto.offset.reset=earliest"
//         }
//     )
//     public void consumeSensorData(String sensorData) {
//         try {
//             DashboardData data = transformToDashboardFormat(sensorData);
//             messagingTemplate.convertAndSend(DASHBOARD_TOPIC, data);
//         } catch (Exception e) {
//             System.err.println("❌ Erreur lors du traitement des données Kafka : " + e.getMessage());
//         }
//     }

//     private DashboardData transformToDashboardFormat(String rawData) throws Exception {
//         JsonNode root = objectMapper.readTree(rawData);

//         JsonNode windowNode = root.path("window");
//         if (windowNode.isMissingNode() || !windowNode.isArray() || windowNode.size() == 0) {
//             throw new Exception("Aucune donnée de capteurs trouvée dans le message Kafka.");
//         }

//         JsonNode firstRow = windowNode.get(0);

//         double[] values = new double[firstRow.size()];
//         for (int i = 0; i < firstRow.size(); i++) {
//             values[i] = firstRow.get(i).asDouble();
//         }

//         long timestamp = System.currentTimeMillis();

//         return new DashboardData(values, timestamp);
//     }

//     public static class DashboardData {
//         private final double[] values;
//         private final long timestamp;

//         public DashboardData(
//                 @JsonProperty("values") double[] values,
//                 @JsonProperty("timestamp") long timestamp
//         ) {
//             this.values = values;
//             this.timestamp = timestamp;
//         }

//         public double[] getValues() {
//             return values;
//         }

//         public long getTimestamp() {
//             return timestamp;
//         }
//     }
// }
package ocp.maintenance.prediction.service;

import java.util.Arrays;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class KafkaConsumerDashboard {

    private final SimpMessagingTemplate messagingTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String DASHBOARD_TOPIC = "/topic/dashboard-data";

    public KafkaConsumerDashboard(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @KafkaListener(
        topics = {"sensor-data"},
        groupId = "dashboard-group",
        properties = {
            "auto.offset.reset=earliest"
        }
    )
    public void consumeSensorData(String sensorData) {
        try {
            System.out.println("✅ Message Kafka reçu : " + sensorData);
            DashboardData data = transformToDashboardFormat(sensorData);
            System.out.println("✅ Valeurs extraites : " + Arrays.toString(data.getValues()));
            messagingTemplate.convertAndSend(DASHBOARD_TOPIC, data);
        } catch (Exception e) {
            System.err.println("❌ Erreur lors du traitement des données Kafka : " + e.getMessage());
        }
    }

    private DashboardData transformToDashboardFormat(String rawData) throws Exception {
        JsonNode root = objectMapper.readTree(rawData);

        JsonNode windowNode = root.path("window");
        if (windowNode.isMissingNode() || !windowNode.isArray() || windowNode.size() == 0) {
            throw new Exception("Aucune donnée de capteurs trouvée dans le message Kafka.");
        }

        JsonNode firstRow = windowNode.get(0);

        // Saute la première colonne (window_index)
        double[] values = new double[firstRow.size() - 1];
        for (int i = 1; i < firstRow.size(); i++) {
            values[i - 1] = firstRow.get(i).asDouble();
        }

        long timestamp = System.currentTimeMillis();

        return new DashboardData(values, timestamp);
    }

    public static class DashboardData {
        private final double[] values;
        private final long timestamp;

        public DashboardData(
                @JsonProperty("values") double[] values,
                @JsonProperty("timestamp") long timestamp
        ) {
            this.values = values;
            this.timestamp = timestamp;
        }

        public double[] getValues() {
            return values;
        }

        public long getTimestamp() {
            return timestamp;
        }
    }
}
