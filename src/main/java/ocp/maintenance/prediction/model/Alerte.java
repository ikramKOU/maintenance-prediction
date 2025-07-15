// /*
//  * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
//  * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
//  */

// package ocp.maintenance.prediction.model;

// import java.time.LocalDateTime;

// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.EnumType;
// import jakarta.persistence.Enumerated;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.ManyToOne;


// @Entity
// public class Alerte {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;
// @Column(columnDefinition = "TEXT")
//     private String message;
//     private String typeAnomalie;

//     @ManyToOne
//     @JoinColumn(name = "equipement_id")
//     private Equipement equipement;

//     @ManyToOne
//     @JoinColumn(name = "employe_id")
//     private Employee employe;

//     @ManyToOne
//     @JoinColumn(name = "capteur_id")
//     private Capteur capteur;


//     @ManyToOne
//     @JoinColumn(name = "anomalie_id")
//     private Anomalie anomalie;
//     private LocalDateTime  dateCreation;


   

//     @ManyToOne
//     @JoinColumn(name = "lu_par_id")
//     private Employee luPar;

//     // Getter/Setter
//     public Employee getLuPar() {
//         return luPar;
//     }
//     @Column(name = "date_prediction")
// private LocalDateTime predictedTime;

// // Getter
// public LocalDateTime getPredictedTime() {
//     return predictedTime;
// }

// // Setter
// public void setPredictedTime(LocalDateTime predictedTime) {
//     this.predictedTime = predictedTime;
// }
//     public void setLuPar(Employee luPar) {
//         this.luPar = luPar;
//     }

  


//        public LocalDateTime getDateCreation() {
//         return dateCreation;
//     }

//     // Setter
//     public void setDateCreation(LocalDateTime dateCreation) {
//         this.dateCreation = dateCreation;
//     }

//     public Anomalie getAnomalie() {
//         return anomalie;
//     }
//     private boolean lue = false;

// // Getter
// public boolean isLue() {
//     return lue;
// }

// // Setter
// public void setLue(boolean lue) {
//     this.lue = lue;
// }
//     @Enumerated(EnumType.STRING)
//     @Column(nullable = false)
//     private StatutAlerte statut;


//     public StatutAlerte getStatut() {
//             return statut;
//         }

//         public void setStatut(StatutAlerte statut) {
//             this.statut = statut;
//         }
//     // Getters
//     public Long getId() {
//         return id;
//     }

//     public String getMessage() {
//         return message;
//     }

//     public String getTypeAnomalie() {
//         return typeAnomalie;
//     }

//     public Equipement getEquipement() {
//         return equipement;
//     }

//     public Employee getEmploye() {
//         return employe;
//     }

//     public Capteur getCapteur() {
//         return capteur;
//     }

//     // Setters
//     public void setId(Long id) {
//         this.id = id;
//     }

//     public void setMessage(String message) {
//         this.message = message;
//     }

//     public void setTypeAnomalie(String typeAnomalie) {
//         this.typeAnomalie = typeAnomalie;
//     }

//     public void setEquipement(Equipement equipement) {
//         this.equipement = equipement;
//     }

//     public void setEmploye(Employee employe) {
//         this.employe = employe;
//     }

//     public void setCapteur(Capteur capteur) {
//         this.capteur = capteur;
//     }

//     // Méthode setAnomalie à implémenter plus tard si nécessaire
//    public void setAnomalie(Anomalie anomalie) {
//     this.anomalie = anomalie;
// }
// }

package ocp.maintenance.prediction.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Alerte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
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

    @Column(name = "date_creation")
    private LocalDateTime dateCreation;

    @Column(name = "date_prediction")
    private LocalDateTime predictedTime;

    private boolean lue = false;

    @ManyToOne
    @JoinColumn(name = "lu_par_id")
    private Employee luPar;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutAlerte statut;

    // Getters et Setters
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

    public Anomalie getAnomalie() {
        return anomalie;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public LocalDateTime getPredictedTime() {
        return predictedTime;
    }

    public boolean isLue() {
        return lue;
    }

    public Employee getLuPar() {
        return luPar;
    }

    public StatutAlerte getStatut() {
        return statut;
    }

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

    public void setAnomalie(Anomalie anomalie) {
        this.anomalie = anomalie;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public void setPredictedTime(LocalDateTime predictedTime) {
        this.predictedTime = predictedTime;
    }

    public void setLue(boolean lue) {
        this.lue = lue;
    }

    public void setLuPar(Employee luPar) {
        this.luPar = luPar;
    }

    public void setStatut(StatutAlerte statut) {
        this.statut = statut;
    }
}

