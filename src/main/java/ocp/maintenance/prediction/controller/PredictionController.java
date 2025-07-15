package ocp.maintenance.prediction.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ocp.maintenance.prediction.model.Prediction;
import ocp.maintenance.prediction.repository.PredictionRepository;
import ocp.maintenance.prediction.service.PredictionSender;


@RestController
@RequestMapping("/api/predictions")
@CrossOrigin("*") // si tu veux autoriser le frontend à accéder
public class PredictionController {


    
    @Autowired
private PredictionSender predictionSender;

public void processAndSendPrediction(Prediction prediction) {
    // ta logique métier ici
    predictionSender.sendPrediction(prediction);
}

    @Autowired
    private PredictionRepository predictionRepository;

    @GetMapping
    public List<Prediction> getAllPredictions() {
        return predictionRepository.findAll();
    }

    @PostMapping
public void addAndSendPrediction(@RequestBody Prediction prediction) {
    // Sauvegarder en base
    predictionRepository.save(prediction);
    // Envoyer via WebSocket
    predictionSender.sendPrediction(prediction);
}
}

