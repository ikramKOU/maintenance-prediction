package ocp.maintenance.prediction.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import ocp.maintenance.prediction.model.Alerte;
import ocp.maintenance.prediction.model.StatutAlerte;
import ocp.maintenance.prediction.repository.AlerteRepository;

// import java.io.BufferedReader;
// import java.nio.file.Files;
// import java.nio.file.Paths;
// import java.time.LocalDateTime;
// import java.util.Optional;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import jakarta.annotation.PostConstruct;
// import ocp.maintenance.prediction.model.Alerte;
// import ocp.maintenance.prediction.model.StatutAlerte;
// import ocp.maintenance.prediction.repository.AlerteRepository;

// @Service
// public class AlerteLogService {

//     @Autowired
//     private AlerteRepository alerteRepository;

//     @Autowired
//     private WebSocketNotifier notifier; // pour diffusion WebSocket

//     private static final String FILE_PATH = "C:\\Users\\ikram\\OneDrive\\Documents\\kafka-simulation\\alerts\\alerts.log"; // ➤ adapte le chemin



//   public Optional<Alerte> findById(Long id) {
//     return alerteRepository.findById(id);
// }

// // public Alerte saveAlerte(Alerte alerte) {
// //     return alerteRepository.save(alerte);
// // }

// public Alerte saveAlerte(Alerte alerte) {
//     return alerteRepository.save(alerte);
// }


//     @PostConstruct
//     public void lireAlertesDepuisFichier() {
//         try (BufferedReader reader = Files.newBufferedReader(Paths.get(FILE_PATH))) {
//             String ligne;
//             while ((ligne = reader.readLine()) != null) {
//                 if (ligne.contains("Anomalie")) {
//                     Alerte alerte = parserLigneEnAlerte(ligne);
//                     if (alerte != null) {
//                         alerteRepository.save(alerte);
//                         notifier.notifyNewAlerte(alerte); // ➤ Notification en temps réel
//                     }
//                 }
//             }
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }

//     public Alerte parserLigneEnAlerte(String ligne) {
//         try {
//             String[] parts = ligne.split(" - ");
//             LocalDateTime dateCreation = LocalDateTime.parse(parts[0].replace(" ", "T"));

//             String message = ligne.trim();

//             String idAnomalieStr = null;
//             if (ligne.contains("#")) {
//                 idAnomalieStr = ligne.split("#")[1].split(" ")[0]; // ex: "114"
//             }

//             String datePrevueStr = null;
//             if (ligne.contains("Prévue pour :")) {
//                 datePrevueStr = ligne.split("Prévue pour :")[1].trim();
//                 if (!datePrevueStr.contains("T")) {
//                     datePrevueStr = datePrevueStr.replace(" ", "T");
//                 }
//             }

//             Alerte alerte = new Alerte();
//             alerte.setDateCreation(dateCreation);
//             alerte.setMessage(message);
//             alerte.setTypeAnomalie("Anomalie #" + idAnomalieStr);
//             alerte.setStatut(StatutAlerte.OUVERTE);

//             // ➤ Si tu veux gérer datePrevue :
//             // alerte.setDatePrevue(LocalDateTime.parse(datePrevueStr));

//             return alerte;

//         } catch (Exception e) {
//             System.err.println("Erreur de parsing : " + ligne);
//             e.printStackTrace();
//             return null;
//         }
//     }

//     public Object findByUsername(String username) {
//         throw new UnsupportedOperationException("Not supported yet.");
//     }
// }

// package ocp.maintenance.prediction.service;

// import java.io.BufferedReader;
// import java.nio.file.Files;
// import java.nio.file.Paths;
// import java.time.LocalDateTime;
// import java.util.Optional;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import jakarta.annotation.PostConstruct;
// import ocp.maintenance.prediction.model.Alerte;
// import ocp.maintenance.prediction.model.StatutAlerte;
// import ocp.maintenance.prediction.repository.AlerteRepository;

// @Service
// public class AlerteLogService {


    
//     @Autowired
//     private AlerteRepository alerteRepository;

//     @Autowired
//     private WebSocketNotifier notifier; // pour diffusion WebSocket

//     private static final String FILE_PATH = "C:\\Users\\ikram\\OneDrive\\Documents\\kafka-simulation\\alerts\\alerts.log"; // adapte le chemin

//     public Optional<Alerte> findById(Long id) {
//         return alerteRepository.findById(id);
//     }

//     // Ajout de @Transactional pour garantir la transaction lors de la sauvegarde
//     @Transactional
//     public Alerte saveAlerte(Alerte alerte) {
//         return alerteRepository.save(alerte);
//     }

//     @PostConstruct
//     public void lireAlertesDepuisFichier() {
//         try (BufferedReader reader = Files.newBufferedReader(Paths.get(FILE_PATH))) {
//             String ligne;
//             while ((ligne = reader.readLine()) != null) {
//                 if (ligne.contains("Anomalie")) {
//                     Alerte alerte = parserLigneEnAlerte(ligne);
//                     if (alerte != null) {
//                         alerteRepository.save(alerte);
//                         notifier.notifyNewAlerte(alerte); // Notification en temps réel
//                     }
//                 }
//             }
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }

//     public Alerte parserLigneEnAlerte(String ligne) {
//         try {
//             String[] parts = ligne.split(" - ");
//             LocalDateTime dateCreation = LocalDateTime.parse(parts[0].replace(" ", "T"));

//             String message = ligne.trim();

//             String idAnomalieStr = null;
//             if (ligne.contains("#")) {
//                 idAnomalieStr = ligne.split("#")[1].split(" ")[0]; // ex: "114"
//             }

//             String datePrevueStr = null;
//             if (ligne.contains("Prévue pour :")) {
//                 datePrevueStr = ligne.split("Prévue pour :")[1].trim();
//                 if (!datePrevueStr.contains("T")) {
//                     datePrevueStr = datePrevueStr.replace(" ", "T");
//                 }
//             }

//             Alerte alerte = new Alerte();
//             alerte.setDateCreation(dateCreation);
//             alerte.setMessage(message);
//             alerte.setTypeAnomalie("Anomalie #" + idAnomalieStr);
//             alerte.setStatut(StatutAlerte.OUVERTE);

//             // Si besoin gérer datePrevue
//             // alerte.setDatePrevue(LocalDateTime.parse(datePrevueStr));

//             return alerte;

//         } catch (Exception e) {
//             System.err.println("Erreur de parsing : " + ligne);
//             e.printStackTrace();
//             return null;
//         }
//     }

//     public Object findByUsername(String username) {
//         throw new UnsupportedOperationException("Not supported yet.");
//     }
// }


@Service
public class AlerteLogService {

    @Autowired
    private AlerteRepository alerteRepository;

    @Autowired
    private WebSocketNotifier notifier;

    private static final String FILE_PATH = "C:\\Users\\ikram\\OneDrive\\Documents\\kafka-simulation\\alerts\\alerts.log";

    public Optional<Alerte> findById(Long id) {
        return alerteRepository.findById(id);
    }

    @Transactional
    public Alerte saveAlerte(Alerte alerte) {
        return alerteRepository.save(alerte);
    }

    @PostConstruct
    public void lireAlertesDepuisFichier() {
        if (!Files.exists(Paths.get(FILE_PATH))) {
            System.err.println("❌ Le fichier d'alertes n'existe pas : " + FILE_PATH);
            return;
        }

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(FILE_PATH))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                if (ligne.contains("Anomalie")) {
                    Alerte alerte = parserLigneEnAlerte(ligne);
                    if (alerte != null) {
                        saveIfNotDuplicateToday(alerte);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("❌ Erreur lors de la lecture du fichier d'alertes : " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Convertit une ligne brute du fichier log en objet Alerte
     */
    public Alerte parserLigneEnAlerte(String ligne) {
        try {
            String[] parts = ligne.split(" - ");
            if (parts.length < 2) {
                System.err.println("❗ Ligne invalide : " + ligne);
                return null;
            }

            LocalDateTime dateCreation = LocalDateTime.parse(parts[0].replace(" ", "T"));
            String message = ligne.trim();

            String idAnomalieStr = null;
            if (ligne.contains("#")) {
                idAnomalieStr = ligne.split("#")[1].split(" ")[0];
            }

            Alerte alerte = new Alerte();
            alerte.setDateCreation(dateCreation);
            alerte.setMessage(message);
            alerte.setTypeAnomalie("Anomalie #" + idAnomalieStr);
            alerte.setStatut(StatutAlerte.OUVERTE);

            // Pour l'instant, on ne lie pas l'alerte à une anomalie spécifique.
            // Quand ton modèle permettra d'extraire des anomalies distinctes,
            // tu pourras ici créer et associer une anomalie.

            return alerte;
        } catch (Exception e) {
            System.err.println("❌ Erreur lors du parsing de la ligne : " + ligne);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Sauvegarde une alerte si elle n'est pas déjà enregistrée aujourd’hui pour le même message
     */
    public void saveIfNotDuplicateToday(Alerte alerte) {
        LocalDateTime creationDate = alerte.getDateCreation();
        LocalDateTime startOfDay = creationDate.toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusNanos(1);

        boolean existsToday = alerteRepository.existsByMessageAndDateCreationBetween(
            alerte.getMessage(), startOfDay, endOfDay
        );

        if (existsToday) {
            System.out.println("🚫 Alerte déjà enregistrée aujourd’hui pour ce message. Ignorée : " + alerte.getMessage());
            return;
        }

        alerteRepository.save(alerte);
        notifier.notifyNewAlerte(alerte);
        System.out.println("✅ Alerte sauvegardée : " + alerte.getMessage());
    }

    public Object findByUsername(String username) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
