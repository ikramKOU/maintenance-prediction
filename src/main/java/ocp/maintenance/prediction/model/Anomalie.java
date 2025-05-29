/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package ocp.maintenance.prediction.model;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;


@Entity
public class Anomalie {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "gravite")
    private GraviteAnomalie gravite;

    @CreationTimestamp
    private Instant timestamp;

    @ManyToOne
    @JoinColumn(name = "capteur_id")
    private Capteur capteur;

    @OneToOne(mappedBy = "anomalie")
    private Alerte alerte;

    public Anomalie() {
    }

    public Anomalie(Long id, String type, String description, GraviteAnomalie gravite, Instant timestamp,
            Capteur capteur, Alerte alerte) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.gravite = gravite;
        this.timestamp = timestamp;
        this.capteur = capteur;
        this.alerte = alerte;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public GraviteAnomalie getGravite() {
        return gravite;
    }

    public void setGravite(GraviteAnomalie gravite) {
        this.gravite = gravite;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Capteur getCapteur() {
        return capteur;
    }

    public void setCapteur(Capteur capteur) {
        this.capteur = capteur;
    }

    public Alerte getAlerte() {
        return alerte;
    }

    public void setAlerte(Alerte alerte) {
        this.alerte = alerte;
    }
}
