package ocp.maintenance.prediction.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import ocp.maintenance.prediction.dto.AlerteRequest;
import ocp.maintenance.prediction.model.Alerte;
import ocp.maintenance.prediction.model.Anomalie;
import ocp.maintenance.prediction.model.Employee;
import ocp.maintenance.prediction.model.Equipement;
import ocp.maintenance.prediction.model.StatutAlerte;
import ocp.maintenance.prediction.repository.AlerteRepository;
import ocp.maintenance.prediction.repository.AnomalieRepository;
import ocp.maintenance.prediction.repository.EmployeRepository;
import ocp.maintenance.prediction.repository.EquipementRepository;
import ocp.maintenance.prediction.service.AlerteLogService;


@RestController
@RequestMapping("/api/alertes")
@CrossOrigin(origins = "http://localhost:5173")  

public class AlerteController {

    private final AlerteRepository alerteRepository;
    private final AnomalieRepository anomalieRepository;
    private final EquipementRepository equipementRepository;
  
    private final EmployeRepository empRepository;
    @Autowired
    private AlerteLogService alerteLogService;

    public AlerteController(AlerteLogService alerteLogService, AlerteRepository alerteRepository, AnomalieRepository anomalieRepository, EmployeRepository empRepository, EquipementRepository equipementRepository) {
        this.alerteLogService = alerteLogService;
        this.alerteRepository = alerteRepository;
        this.anomalieRepository = anomalieRepository;
        this.empRepository = empRepository;
        this.equipementRepository = equipementRepository;
    }


  @PutMapping("/{id}/lire/{operateurId}")
// public ResponseEntity<?> marquerCommeLue(@PathVariable Long id, @PathVariable Long operateurId) {
//     Optional<Alerte> optionalAlerte = alerteRepository.findById(id);
//     Optional<Employee> optionalEmployee = empRepository.findById(operateurId);

//     if (optionalAlerte.isEmpty() || optionalEmployee.isEmpty()) {
//         return ResponseEntity.notFound().build();
//     }

//     Alerte alerte = optionalAlerte.get();
//     alerte.setLuPar(optionalEmployee.get());

//     alerteRepository.save(alerte);
//     return ResponseEntity.ok("Alerte marked as read by operator.");
// }
public ResponseEntity<?> marquerCommeLue(@PathVariable Long id, @PathVariable Long operateurId) {
    Optional<Alerte> optionalAlerte = alerteRepository.findById(id);
    Optional<Employee> optionalEmployee = empRepository.findById(operateurId);

    if (optionalAlerte.isEmpty() || optionalEmployee.isEmpty()) {
        return ResponseEntity.notFound().build();
    }

    Alerte alerte = optionalAlerte.get();
    alerte.setLuPar(optionalEmployee.get());
    alerte.setLue(true);

    alerteRepository.save(alerte);
    return ResponseEntity.ok("Alerte marquée comme lue.");
}


     // ✅ 2. Assigner un technicien
    @PutMapping("/{id}/assigner-technicien/{technicienId}")
    public ResponseEntity<?> assignerTechnicien(@PathVariable Long id, @PathVariable Long technicienId) {
        Optional<Alerte> optionalAlerte = alerteRepository.findById(id);
        Optional<Employee> optionalTechnicien = empRepository.findById(technicienId);

        if (optionalAlerte.isEmpty() || optionalTechnicien.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Alerte alerte = optionalAlerte.get();
        alerte.setEmploye(optionalTechnicien.get()); // ✅ Utilise le champ existant
        alerteRepository.save(alerte);

        return ResponseEntity.ok("Technician assigned successfully.");
    }


    // ✅ 3. Clôturer une alerte
    @PutMapping("/{id}/cloturer")
    public ResponseEntity<?> cloturerAlerte(@PathVariable Long id) {
        Optional<Alerte> optionalAlerte = alerteRepository.findById(id);
        if (optionalAlerte.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Alerte alerte = optionalAlerte.get();
        alerte.setStatut(StatutAlerte.CLOTUREE);
        alerteRepository.save(alerte);
        return ResponseEntity.ok("Alerte closed successfully.");
    }
@PostMapping("/save-if-not-duplicate")
    public ResponseEntity<String> saveIfNotDuplicate(@RequestBody Alerte alerte) {
        try {
            alerteLogService.saveIfNotDuplicateToday(alerte);
            return ResponseEntity.ok("✅ Alerte traitée avec succès.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("❌ Erreur : " + e.getMessage());
        }
    }
    
@GetMapping("/{id}")
// @PreAuthorize("hasRole('OPERATEUR')")  // ou ajuste selon ta sécurité
public ResponseEntity<Alerte> getAlerteById(@PathVariable Long id) {
    Alerte alerte = alerteRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Alerte non trouvée"));
    return ResponseEntity.ok(alerte);
}
@GetMapping("/all")
// @PreAuthorize("hasRole('OPERATEUR')")  // si tu veux gérer la sécurité
public ResponseEntity<List<Alerte>> getAllAlertes() {
    List<Alerte> alertes = alerteRepository.findAll();
    return ResponseEntity.ok(alertes);
}

    @PostMapping
    @PreAuthorize("hasRole('OPERATEUR')")
    public ResponseEntity<?> createAlerte(@Valid @RequestBody AlerteRequest request) {
        try {
            Alerte alerte = new Alerte();
            
            // 1. Traitement prioritaire par anomalie
            if (request.getAnomalieId() != null) {
                Anomalie anomalie = anomalieRepository.findById(request.getAnomalieId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anomalie non trouvée"));
                
                alerte.setAnomalie(anomalie);
                alerte.setEquipement(anomalie.getCapteur().getEquipement());
                alerte.setMessage(request.getMessage() != null ? 
                                request.getMessage() : 
                                "Alerte pour anomalie: " + anomalie.getType());
            }
            // 2. Fallback: Création directe par équipement
            else if (request.getEquipementId() != null) {
                Equipement equipement = equipementRepository.findById(request.getEquipementId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Équipement non trouvé"));
                
                alerte.setEquipement(equipement);
                alerte.setMessage(request.getMessage() != null ? 
                                request.getMessage() : 
                                "Alerte manuelle pour équipement: " + equipement.getType());
            }
            else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "AnomalieId ou EquipementId requis");
            }

            alerte.setStatut(StatutAlerte.OUVERTE);
            alerte.setDateCreation(LocalDateTime.now());
            
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(alerteRepository.save(alerte));
                    
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur de création: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
@PreAuthorize("hasRole('OPERATEUR')") // Ajuste selon ta sécurité
public ResponseEntity<?> deleteAlerte(@PathVariable Long id) {
    return alerteRepository.findById(id).map(alerte -> {
        alerteRepository.delete(alerte);
        return ResponseEntity.ok("🗑 Alerte supprimée avec succès");
    }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Alerte non trouvée"));
}

}