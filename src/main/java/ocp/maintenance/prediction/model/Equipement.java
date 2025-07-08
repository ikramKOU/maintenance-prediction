// /*
//  * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
//  * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
//  */

// package ocp.maintenance.prediction.model;

// import java.util.List;

// import jakarta.persistence.CascadeType;
// import jakarta.persistence.Entity;
// import jakarta.persistence.EnumType;
// import jakarta.persistence.Enumerated;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.OneToMany;


// @Entity
// public class Equipement {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     private String type;

//     @Enumerated(EnumType.STRING)
//     private EtatEquipement etat;

//     @OneToMany(mappedBy = "equipement")
//     private List<Capteur> capteurs;

//     @OneToMany(mappedBy = "equipement")
//     private List<Alerte> alertes;
//     @OneToMany(mappedBy = "equipement", cascade = CascadeType.ALL)
//     private List<Intervention> interventions;

//     // Constructeur vide requis par JPA
//     public Equipement() {
//     }

//     // Getters et setters

//     public Long getId() {
//         return id;
//     }

//     public void setId(Long id) {
//         this.id = id;
//     }

//     public String getType() {
//         return type;
//     }

//     public void setType(String type) {
//         this.type = type;
//     }

//     public EtatEquipement getEtat() {
//         return etat;
//     }

//     public void setEtat(EtatEquipement etat) {
//         this.etat = etat;
//     }

//     public List<Capteur> getCapteurs() {
//         return capteurs;
//     }

//     public void setCapteurs(List<Capteur> capteurs) {
//         this.capteurs = capteurs;
//     }

//     public List<Alerte> getAlertes() {
//         return alertes;
//     }

//     public void setAlertes(List<Alerte> alertes) {
//         this.alertes = alertes;
//     }

//     @Override
//     public String toString() {
//         return "Equipement{" +
//                 "id=" + id +
//                 ", type='" + type + '\'' +
//                 ", etat=" + etat +
//                 '}';
//     }
// }




package ocp.maintenance.prediction.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Equipement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private String type;

    @Enumerated(EnumType.STRING)
    private EtatEquipement etat;

    @OneToMany(mappedBy = "equipement")
    private List<Capteur> capteurs;

    @OneToMany(mappedBy = "equipement")
    private List<Alerte> alertes;

    
    @OneToMany(mappedBy = "equipement")
    @JsonIgnore
    private List<Intervention> interventions;

    public Equipement() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public EtatEquipement getEtat() {
        return etat;
    }

    public void setEtat(EtatEquipement etat) {
        this.etat = etat;
    }

    public List<Capteur> getCapteurs() {
        return capteurs;
    }

    public void setCapteurs(List<Capteur> capteurs) {
        this.capteurs = capteurs;
    }

    public List<Alerte> getAlertes() {
        return alertes;
    }

    public void setAlertes(List<Alerte> alertes) {
        this.alertes = alertes;
    }

    public List<Intervention> getInterventions() {
        return interventions;
    }

    public void setInterventions(List<Intervention> interventions) {
        this.interventions = interventions;
    }

    @Override
    public String toString() {
        return "Equipement{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", etat=" + etat +
                '}';
    }
}
