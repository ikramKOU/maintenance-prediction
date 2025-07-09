package ocp.maintenance.prediction.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class StompWebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Définir les endpoints WebSocket avec SockJS (fallback pour navigateurs sans support WS)
        registry.addEndpoint("/ws-sensor-data").setAllowedOrigins("*");
        registry.addEndpoint("/ws-alerts").setAllowedOrigins("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // /topic est le préfixe des destinations pour le client abonné (ex: /topic/alertes)
        registry.enableSimpleBroker("/topic");

        // /app est le préfixe utilisé côté client pour envoyer les messages (ex: /app/alerte)
        registry.setApplicationDestinationPrefixes("/app");
    }
}
