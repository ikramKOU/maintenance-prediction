package ocp.maintenance.prediction.dto;

public class CapteurDTO {
    private String type;
    private Double valeur;
    private String timestamp; 
    private Long equipementId;

    // Getters
    public String getType() {
        return type;
    }

    public Double getValeur() {
        return valeur;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public Long getEquipementId() {
        return equipementId;
    }

    // Setters
    public void setType(String type) {
        this.type = type;
    }

    public void setValeur(Double valeur) {
        this.valeur = valeur;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setEquipementId(Long equipementId) {
        this.equipementId = equipementId;
    }
}
