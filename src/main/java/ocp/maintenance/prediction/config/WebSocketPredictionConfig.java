package ocp.maintenance.prediction.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.socket.WebSocketHandler;

@Configuration
@EnableWebSocket
@ComponentScan(basePackages = "ocp.maintenance.handler")
public class WebSocketPredictionConfig implements WebSocketConfigurer {

    @Autowired
    // @Qualifier("predictionWebSocketHandler")
    private WebSocketHandler predictionWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
  System.out.println("Registering WebSocket handler...");
    registry.addHandler(predictionWebSocketHandler, "/ws-predictions").setAllowedOrigins("*");    }
}
