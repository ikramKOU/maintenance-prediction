package ocp.maintenance.prediction.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ocp.maintenance.prediction.model.Capteur;
import ocp.maintenance.prediction.service.CapteurService;

@RestController
@RequestMapping("/api/capteurs")
@CrossOrigin(origins = "*")
public class CapteurController {

    private final CapteurService capteurService;

    public CapteurController(CapteurService capteurService) {
        this.capteurService = capteurService;
    }

    @PostMapping("/add")
    public ResponseEntity<Capteur> create(@RequestBody Capteur capteur) {
        return ResponseEntity.ok(capteurService.save(capteur));
    }

    @GetMapping
    public List<Capteur> getAll() {
        return capteurService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Capteur> getById(@PathVariable Long id) {
        Optional<Capteur> capteur = capteurService.findById(id);
        return capteur.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/equipement/{equipementId}")
    public List<Capteur> getByEquipementId(@PathVariable Long equipementId) {
        return capteurService.findByEquipementId(equipementId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Capteur> update(@PathVariable Long id, @RequestBody Capteur capteur) {
        if (!capteurService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        capteur.setId(id);
        return ResponseEntity.ok(capteurService.save(capteur));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        capteurService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
