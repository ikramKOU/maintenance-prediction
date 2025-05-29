package ocp.maintenance.prediction.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ocp.maintenance.prediction.repository.AlerteRepository;
import ocp.maintenance.prediction.repository.AnomalieRepository;
import ocp.maintenance.prediction.dto.AlerteRequest;
import ocp.maintenance.prediction.model.Alerte;
import ocp.maintenance.prediction.model.Anomalie;
import ocp.maintenance.prediction.model.StatutAlerte;

import org.springframework.http.HttpStatus;


import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import ocp.maintenance.prediction.model.Equipement;
import ocp.maintenance.prediction.repository.EquipementRepository;
import java.time.LocalDateTime;


@RestController
@RequestMapping("/api/alertes")
public class AlerteController {

    private final AlerteRepository alerteRepository;
    private final AnomalieRepository anomalieRepository;
    private final EquipementRepository equipementRepository;

    public AlerteController(AlerteRepository alerteRepository, 
                          AnomalieRepository anomalieRepository,
                          EquipementRepository equipementRepository) {
        this.alerteRepository = alerteRepository;
        this.anomalieRepository = anomalieRepository;
        this.equipementRepository = equipementRepository;
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
}