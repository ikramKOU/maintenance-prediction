package ocp.maintenance.prediction.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import ocp.maintenance.prediction.model.Prediction;
import ocp.maintenance.prediction.repository.PredictionRepository;



import java.time.LocalDateTime;
import java.util.Map;

@Component
public class PredictionConsumer {

    @Autowired
    private PredictionRepository predictionRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "alertes-data", groupId = "group_id")
    public void consume(String message) {
        try {
            // 🔍 Convertir le JSON en Map
            Map<String, Object> data = objectMapper.readValue(message, Map.class);

            // Extraire les champs
            LocalDateTime timestamp = LocalDateTime.parse((String) data.get("timestamp"));
            int predictionValue = (int) data.get("prediction");
            String msg = (String) data.get("message");

            // Créer et sauvegarder la prédiction
            Prediction prediction = new Prediction(timestamp, predictionValue, msg);
            predictionRepository.save(prediction);

            // Envoi WebSocket
            messagingTemplate.convertAndSend("/topic/predictions", prediction);

            System.out.println("✅ Prédiction sauvegardée et envoyée : " + prediction);
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la consommation Kafka : " + e.getMessage());
        }
    }
}
