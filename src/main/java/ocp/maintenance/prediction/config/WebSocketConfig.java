package ocp.maintenance.prediction.config;


// import org.springframework.context.annotation.Configuration;
// import org.springframework.messaging.simp.config.MessageBrokerRegistry;
// import org.springframework.web.socket.config.annotation.*;

// @Configuration
// @EnableWebSocketMessageBroker
// public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

//     @Override
//     public void registerStompEndpoints(StompEndpointRegistry registry) {
//         registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS(); // endpoint côté client
//     }

//     @Override
//     public void configureMessageBroker(MessageBrokerRegistry config) {
//         config.enableSimpleBroker("/topic"); // chemin des notifs
//         config.setApplicationDestinationPrefixes("/app"); // préfixe pour envoi client → serveur
//     }
// }
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");  // broker simple en mémoire
        config.setApplicationDestinationPrefixes("/app");
    }


    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();
    }
}
