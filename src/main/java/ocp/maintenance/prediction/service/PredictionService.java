package ocp.maintenance.prediction.service;

import ocp.maintenance.prediction.model.Prediction;
import ocp.maintenance.prediction.repository.PredictionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PredictionService {


    @Autowired
    private PredictionRepository predictionRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public Prediction enregistrerPrediction(LocalDateTime datePrediction, int value, String message) {
    System.out.println("🟡 [PredictionService] Requête reçue pour enregistrer une prédiction.");

    Prediction prediction = new Prediction();
    prediction.setTimestamp(datePrediction);
    prediction.setPrediction(value);
    prediction.setMessage(message);

    Prediction savedPrediction = predictionRepository.save(prediction);

    System.out.println("✅ [PredictionService] Prédiction enregistrée en BDD avec ID = " + savedPrediction.getId());

    broadcastPrediction(savedPrediction);

    return savedPrediction;
}

public void broadcastPrediction(Prediction prediction) {
        messagingTemplate.convertAndSend("/topic/predictions", prediction);
    }
}