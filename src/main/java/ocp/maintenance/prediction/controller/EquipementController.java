package ocp.maintenance.prediction.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ocp.maintenance.prediction.model.Equipement;
import ocp.maintenance.prediction.model.EtatEquipement;
import ocp.maintenance.prediction.repository.EquipementRepository;

@RestController
@RequestMapping("/api/equipements")
public class EquipementController {

    private final EquipementRepository equipementRepository;

    public EquipementController(EquipementRepository equipementRepository) {
        this.equipementRepository = equipementRepository;
    }

    @GetMapping
    public ResponseEntity<List<Equipement>> getAllEquipements() {
        return ResponseEntity.ok(equipementRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipement> getEquipementById(@PathVariable Long id) {
        return equipementRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/etat")
    @PreAuthorize("hasRole('TECHNICIEN')")
    public ResponseEntity<Equipement> updateEtat(
            @PathVariable Long id,
            @RequestBody Map<String, String> payload) {
        
        return equipementRepository.findById(id)
                .map(equipement -> {
                    equipement.setEtat(EtatEquipement.valueOf(payload.get("etat")));
                    return ResponseEntity.ok(equipementRepository.save(equipement));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
