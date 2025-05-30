/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package ocp.maintenance.prediction.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity
public class Alerte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;
    private String typeAnomalie;

    @ManyToOne
    @JoinColumn(name = "equipement_id")
    private Equipement equipement;

    @ManyToOne
    @JoinColumn(name = "employe_id")
    private Employee employe;

    @ManyToOne
    @JoinColumn(name = "capteur_id")
    private Capteur capteur;


    @ManyToOne
    @JoinColumn(name = "anomalie_id")
    private Anomalie anomalie;
    private LocalDateTime  dateCreation;


       public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    // Setter
    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Anomalie getAnomalie() {
        return anomalie;
    }

    private StatutAlerte statut;


    public StatutAlerte getStatut() {
            return statut;
        }

        public void setStatut(StatutAlerte statut) {
            this.statut = statut;
        }
    // Getters
    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getTypeAnomalie() {
        return typeAnomalie;
    }

    public Equipement getEquipement() {
        return equipement;
    }

    public Employee getEmploye() {
        return employe;
    }

    public Capteur getCapteur() {
        return capteur;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTypeAnomalie(String typeAnomalie) {
        this.typeAnomalie = typeAnomalie;
    }

    public void setEquipement(Equipement equipement) {
        this.equipement = equipement;
    }

    public void setEmploye(Employee employe) {
        this.employe = employe;
    }

    public void setCapteur(Capteur capteur) {
        this.capteur = capteur;
    }

    // Méthode setAnomalie à implémenter plus tard si nécessaire
   public void setAnomalie(Anomalie anomalie) {
    this.anomalie = anomalie;
}
}
