// package ocp.maintenance.prediction.service;

// import java.time.LocalDateTime;

// import org.springframework.kafka.annotation.KafkaListener;
// import org.springframework.stereotype.Service;

// import ocp.maintenance.prediction.model.Alerte;
// import ocp.maintenance.prediction.model.StatutAlerte;
// import ocp.maintenance.prediction.repository.AlerteRepository;

// @Service
// public class AlerteKafkaConsumer {

//     private final AlerteRepository alerteRepository;
//     private final WebSocketNotifier webSocketNotifier;

//     public AlerteKafkaConsumer(AlerteRepository alerteRepository, WebSocketNotifier webSocketNotifier) {
//         this.alerteRepository = alerteRepository;
//         this.webSocketNotifier = webSocketNotifier;
//     }

//     @KafkaListener(
//         topics = "alertes-data",
//         groupId = "alerte-group",
//         properties = {
//             "auto.offset.reset=earliest"
//         }
//     )
//     public void listen(String message) {
//         // 👉 Ignorer les messages ne contenant pas "Anomalie" ou "Prévue pour"
//         if (!message.contains("Anomalie") || !message.contains("Prévue pour")) {
//             return;
//         }

//         // ✅ Créer l'alerte
//         Alerte alerte = parseMessageToAlerte(message);

//         if (alerte != null) {
//             alerteRepository.save(alerte);
//             webSocketNotifier.notifyNewAlerte(alerte);
//             System.out.println("✅ Alerte enregistrée : " + alerte.getMessage());
//         }
//     }

//     private Alerte parseMessageToAlerte(String message) {
//         try {
//             Alerte alerte = new Alerte();
//             alerte.setMessage(message);
//             alerte.setDateCreation(LocalDateTime.now());
//             alerte.setStatut(StatutAlerte.OUVERTE);
//             alerte.setTypeAnomalie("Anomalie");
//             return alerte;
//         } catch (Exception e) {
//             e.printStackTrace();
//             return null;
//         }
//     }
// }

// // import java.time.LocalDateTime;
// // import java.time.format.DateTimeFormatter;

// // import org.springframework.kafka.annotation.KafkaListener;
// // import org.springframework.stereotype.Service;

// // import ocp.maintenance.prediction.model.Alerte;
// // import ocp.maintenance.prediction.model.StatutAlerte;
// // import ocp.maintenance.prediction.repository.AlerteRepository;
// // import ocp.maintenance.prediction.service.WebSocketNotifier;

// // @Service
// // public class AlerteKafkaConsumer {

// //     private final AlerteRepository alerteRepository;
// //     private final WebSocketNotifier webSocketNotifier;

// //     private Alerte previousAlerte = null;

// //     public AlerteKafkaConsumer(AlerteRepository alerteRepository, WebSocketNotifier webSocketNotifier) {
// //         this.alerteRepository = alerteRepository;
// //         this.webSocketNotifier = webSocketNotifier;
// //     }

// //     @KafkaListener(
// //         topics = "alertes-data",
// //         groupId = "alerte-group",
// //         properties = {
// //             "auto.offset.reset=earliest"
// //         }
// //     )
// //     public void listen(String message) {
// //         if (message == null || !message.contains("Anomalie") || !message.contains("Prévue pour")) {
// //             return;
// //         }

// //         Alerte alerte = parseMessageToAlerte(message);

// //         if (alerte != null && !isEquals(previousAlerte, alerte)) {
// //             alerteRepository.save(alerte);
// //             webSocketNotifier.notifyNewAlerte(alerte);
// //             System.out.println("✅ Alerte enregistrée : " + alerte.getMessage());

// //             saveCurrentAlerte(alerte);
// //         } else {
// //             System.out.println("🔁 Alerte déjà affichée. Ignorée.");
// //         }
// //     }

// //     private Alerte parseMessageToAlerte(String message) {
// //         try {
// //             Alerte alerte = new Alerte();
// //             alerte.setMessage(message);
// //             alerte.setDateCreation(LocalDateTime.now());
// //             alerte.setStatut(StatutAlerte.OUVERTE);
// //             alerte.setTypeAnomalie("Anomalie");
// //             return alerte;
// //         } catch (Exception e) {
// //             e.printStackTrace();
// //             return null;
// //         }
// //     }

// //     private boolean isEquals(Alerte previous, Alerte current) {
// //         if (previous == null || current == null) return false;
// //         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
// //         return previous.getDateCreation().format(formatter).equals(current.getDateCreation().format(formatter));
// //     }

// //     private void saveCurrentAlerte(Alerte alerte) {
// //         this.previousAlerte = alerte;
// //     }
// // }

package ocp.maintenance.prediction.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import ocp.maintenance.prediction.model.Alerte;
import ocp.maintenance.prediction.model.StatutAlerte;
import ocp.maintenance.prediction.repository.AlerteRepository;

@Service
public class AlerteKafkaConsumer {

    private final AlerteRepository alerteRepository;
    private final WebSocketNotifier webSocketNotifier;

    /**
     * Liste temporaire d’alertes reçues pendant la fenêtre temporelle
     */
    private final List<Alerte> bufferAlertes = Collections.synchronizedList(new ArrayList<>());

    /**
     * Executor pour planifier la tâche
     */
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    /**
     * Future du timer en cours
     */
    private ScheduledFuture<?> currentTimer = null;

    /**
     * Durée de la fenêtre (en ms)
     */
    private static final long BATCH_WINDOW_MS = 10_000;

    public AlerteKafkaConsumer(AlerteRepository alerteRepository, WebSocketNotifier webSocketNotifier) {
        this.alerteRepository = alerteRepository;
        this.webSocketNotifier = webSocketNotifier;
    }

    @KafkaListener(
        topics = "alertes-data",
        groupId = "alerte-group",
        properties = {
            "auto.offset.reset=earliest"
        }
    )
    public void listen(String message) {
        if (message == null || !message.contains("Anomalie") || !message.contains("Prévue pour")) {
            return;
        }

        Alerte alerte = parseMessageToAlerte(message);

        if (alerte != null) {
            bufferAlertes.add(alerte);

            // Démarrer le timer s’il n’est pas déjà en cours
            if (currentTimer == null || currentTimer.isDone()) {
                currentTimer = scheduler.schedule(this::flushBuffer, BATCH_WINDOW_MS, TimeUnit.MILLISECONDS);
            }
        }
    }

    /**
     * Méthode appelée quand la fenêtre temporelle se termine
     */
    private void flushBuffer() {
        List<Alerte> batch;

        synchronized (bufferAlertes) {
            if (bufferAlertes.isEmpty()) {
                return;
            }
            batch = new ArrayList<>(bufferAlertes);
            bufferAlertes.clear();
        }

        System.out.println("🔔 Envoi groupé de " + batch.size() + " alertes au websocket.");

        for (Alerte alerte : batch) {
            // Optionnel : stocker si souhaité
            alerteRepository.save(alerte);
        }

        // Envoi groupé : tu peux envoyer la liste si ton front le supporte,
        // sinon les envoyer une par une comme ci-dessous :
        for (Alerte alerte : batch) {
            webSocketNotifier.notifyNewAlerte(alerte);
        }
    }

    private Alerte parseMessageToAlerte(String message) {
        try {
            Alerte alerte = new Alerte();
            alerte.setMessage(message);
            alerte.setDateCreation(LocalDateTime.now());
            alerte.setStatut(StatutAlerte.OUVERTE);
            alerte.setTypeAnomalie("Anomalie");
            return alerte;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
