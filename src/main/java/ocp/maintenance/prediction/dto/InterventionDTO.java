package ocp.maintenance.prediction.dto;

public class InterventionDTO {

    private Long alerte;
    private String dateDebut;
    private String dateFin;
    private String statut;
    private String details;
    private Long employe;
    private Long equipement;

    public Long getAlerte() {
        return alerte;
    }

    public void setAlerte(Long alerte) {
        this.alerte = alerte;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Long getEmploye() {
        return employe;
    }

    public void setEmploye(Long employe) {
        this.employe = employe;
    }

    public Long getEquipement() {
        return equipement;
    }

    public void setEquipement(Long equipement) {
        this.equipement = equipement;
    }
}
