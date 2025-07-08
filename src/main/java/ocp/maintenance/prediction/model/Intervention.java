/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package ocp.maintenance.prediction.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
public class Intervention {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "alerte_id")
    private Alerte alerte;
    
  

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
private LocalDateTime dateDebut;

@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
private LocalDateTime dateFin;

    
    @Enumerated(EnumType.STRING)
    private StatutIntervention statut;

    @Column(columnDefinition = "TEXT")
    private String details;

    @ManyToOne
    @JoinColumn(name = "employe_id")
    private Employee employe;

    @ManyToOne
    @JoinColumn(name = "equipement_id")
    @JsonManagedReference
    private Equipement equipement;


    public StatutIntervention getStatut() {
    return statut;
    }

    public void setStatut(StatutIntervention statut) {
        this.statut = statut;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDateTime getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDateTime dateFin) {
        this.dateFin = dateFin;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Employee getEmploye() {
        return employe;
    }

    public void setEmploye(Employee employe) {
        this.employe = employe;
    }

    public Equipement getEquipement() {
        return equipement;
    }

    public void setEquipement(Equipement equipement) {
        this.equipement = equipement;
    }

    public Alerte getAlerte() {
        return alerte;
    }

    public void setAlerte(Alerte alerte) {
        this.alerte = alerte;
    }

}

