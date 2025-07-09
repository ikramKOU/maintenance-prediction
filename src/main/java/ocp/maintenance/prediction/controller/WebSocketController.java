package ocp.maintenance.prediction.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // Route pour les données brutes (utilisée par dashboard_consumer.py)
    @MessageMapping("/sensor-data")
    public void handleSensorData(String message) {
        messagingTemplate.convertAndSend("/topic/sensor-updates", message);
    }

    // Route pour les alertes (utilisée par consumer.py)
    @MessageMapping("/alerts")
    public void handleAlert(String alert) {
        messagingTemplate.convertAndSend("/topic/prediction-alerts", alert);
    }
}