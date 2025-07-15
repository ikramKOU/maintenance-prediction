package ocp.maintenance.prediction.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import ocp.maintenance.prediction.model.Prediction;

@Service
public class PredictionSender {

    private final SimpMessagingTemplate messagingTemplate;

    public PredictionSender(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendPrediction(Prediction prediction) {
        // Envoie la prédiction à tous les abonnés du topic /topic/predictions
        messagingTemplate.convertAndSend("/topic/predictions", prediction);
    }
}
