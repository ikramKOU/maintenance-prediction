package ocp.maintenance.prediction.dto;

// AlerteRequest.java (nouveau fichier)
public class AlerteRequest {
    private Long anomalieId;
    private String message;  // Optionnel
    private Long equipementId;  // Optionnel si anomalieId est fourni

    // Getters et Setters
    public Long getAnomalieId() {
        return anomalieId;
    }

    public void setAnomalieId(Long anomalieId) {
        this.anomalieId = anomalieId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getEquipementId() {
        return equipementId;
    }

    public void setEquipementId(Long equipementId) {
        this.equipementId = equipementId;
    }
}