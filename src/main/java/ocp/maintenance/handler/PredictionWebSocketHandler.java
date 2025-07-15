package ocp.maintenance.handler;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;



@Component("predictionWebSocketHandler")
public class PredictionWebSocketHandler extends TextWebSocketHandler {

    @Override
public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    System.out.println("Nouvelle connexion WebSocket : " + session.getId());
    super.afterConnectionEstablished(session);
}
    @Override
    public void handleTextMessage(org.springframework.web.socket.WebSocketSession session, org.springframework.web.socket.TextMessage message) throws Exception {
        System.out.println("Message reçu : " + message.getPayload());
        session.sendMessage(new org.springframework.web.socket.TextMessage("Réponse du serveur"));
    }
}
