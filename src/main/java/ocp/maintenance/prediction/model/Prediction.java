package ocp.maintenance.prediction.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "prediction")
public class Prediction  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    private int prediction; // 0 ou 1

    @Column(columnDefinition = "TEXT")
    private String message;

    public Prediction() {}

    public Prediction(LocalDateTime timestamp, int prediction, String message) {
        this.timestamp = timestamp;
        this.prediction = prediction;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getPrediction() {
        return prediction;
    }

    public void setPrediction(int prediction) {
        this.prediction = prediction;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Prediction{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", prediction=" + prediction +
                ", message='" + message + '\'' +
                '}';
    }
}
