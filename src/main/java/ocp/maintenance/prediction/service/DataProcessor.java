package ocp.maintenance.prediction.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class DataProcessor {

    private static final Logger logger = LoggerFactory.getLogger(DataProcessor.class);
    
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final SimpMessagingTemplate messagingTemplate;

    // Injection des dépendances via constructeur
    public DataProcessor(
        ObjectMapper objectMapper,
        KafkaTemplate<String, String> kafkaTemplate, 
        SimpMessagingTemplate messagingTemplate
    ) {
        this.objectMapper = objectMapper;
        this.kafkaTemplate = kafkaTemplate;
        this.messagingTemplate = messagingTemplate;
    }

    @KafkaListener(
        topics = "sensor-data",
        groupId = "data-processor-group",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void process(ConsumerRecord<String, String> record) {
        try {
            JsonNode rawData = objectMapper.readTree(record.value());
            
            // 1. Validation
            if (!isValidData(rawData)) {
                logger.warn("Données invalides reçues pour la clé : {}", record.key());
                return;
            }

            // 2. Transformation
            ProcessedData processed = transformData(rawData);

            // 3. Routage
            if (processed.isAnomaly()) {
                kafkaTemplate.send("alert-events", objectMapper.writeValueAsString(processed));
            }
            
            messagingTemplate.convertAndSend("/topic/processed-data", processed);

        } catch (Exception e) {
            logger.error("Erreur de traitement pour la clé {} :", record.key(), e);
        }
    }

    private boolean isValidData(JsonNode data) {
        return data != null &&
               data.has("window_index") &&
               data.has("window") &&
               data.get("window").isArray() &&
               data.get("window").size() > 0;
    }

    private ProcessedData transformData(JsonNode raw) {
        // Implémente ta logique de transformation ici
        return new ProcessedData(
            raw.get("window_index").asInt(),
            extractMainValue(raw),
            extractSecondaryValues(raw),
            checkAnomaly(raw)
        );
    }

    private double extractMainValue(JsonNode raw) {
        // Exemple d'extraction, à adapter
        return raw.has("main_value") ? raw.get("main_value").asDouble() : 0.0;
    }

    private double[] extractSecondaryValues(JsonNode raw) {
        // Exemple d'extraction, à adapter
        if (raw.has("secondary_values") && raw.get("secondary_values").isArray()) {
            JsonNode arrayNode = raw.get("secondary_values");
            double[] values = new double[arrayNode.size()];
            for (int i = 0; i < arrayNode.size(); i++) {
                values[i] = arrayNode.get(i).asDouble();
            }
            return values;
        }
        return new double[0];
    }

    private boolean checkAnomaly(JsonNode raw) {
        // Exemple de logique d'anomalie, à définir
        return raw.has("anomaly") && raw.get("anomaly").asBoolean();
    }

    // Classe interne représentant les données traitées
    private static class ProcessedData {
        private final int windowId;
        private final double mainValue;
        private final double[] secondaryValues;
        private final boolean anomaly;

        public ProcessedData(int windowId, double mainValue, double[] secondaryValues, boolean anomaly) {
            this.windowId = windowId;
            this.mainValue = mainValue;
            this.secondaryValues = secondaryValues;
            this.anomaly = anomaly;
        }

        public int getWindowId() {
            return windowId;
        }

        public double getMainValue() {
            return mainValue;
        }

        public double[] getSecondaryValues() {
            return secondaryValues;
        }

        public boolean isAnomaly() {
            return anomaly;
        }
    }
}
