/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package ocp.maintenance.prediction.model;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Capteur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private Double valeur;
    private Instant timestamp;

    @ManyToOne
    @JoinColumn(name = "equipement_id")
    private Equipement equipement;

    // Getters
    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Double getValeur() {
        return valeur;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Equipement getEquipement() {
        return equipement;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setValeur(Double valeur) {
        this.valeur = valeur;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public void setEquipement(Equipement equipement) {
        this.equipement = equipement;
    }
}
