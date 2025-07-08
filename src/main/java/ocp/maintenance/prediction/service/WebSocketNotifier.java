package ocp.maintenance.prediction.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ocp.maintenance.prediction.model.Alerte;

@Service
public class WebSocketNotifier {

    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketNotifier(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void notifyNewAlerte(Alerte alerte) {
        messagingTemplate.convertAndSend("/topic/alertes", alerte);
    }
}
