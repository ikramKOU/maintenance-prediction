package ocp.maintenance.prediction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MaintenancePredictionApplication {
    public static void main(String[] args) {
        SpringApplication.run(MaintenancePredictionApplication.class, args);
        		System.out.println("Application is running on port: " + System.getProperty("server.port", "8080"));

    }
}
