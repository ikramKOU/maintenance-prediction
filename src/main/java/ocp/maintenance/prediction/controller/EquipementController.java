// package ocp.maintenance.prediction.controller;

// import java.util.List;
// import java.util.Map;

// import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import ocp.maintenance.prediction.model.Equipement;
// import ocp.maintenance.prediction.model.EtatEquipement;
// import ocp.maintenance.prediction.repository.EquipementRepository;

// @RestController
// @RequestMapping("/api/equipements")
// public class EquipementController {

//     private final EquipementRepository equipementRepository;





//     public EquipementController(EquipementRepository equipementRepository) {
//         this.equipementRepository = equipementRepository;
//     }


//         @PostMapping
//         @PreAuthorize("hasRole('ADMINISTRATEUR')") 
//         public ResponseEntity<Equipement> createEquipement(@RequestBody Equipement equipement) {
//             Equipement savedEquipement = equipementRepository.save(equipement);
//             return ResponseEntity.ok(savedEquipement);
//         }

//     @GetMapping
//     public ResponseEntity<List<Equipement>> getAllEquipements() {
//         return ResponseEntity.ok(equipementRepository.findAll());
//     }

//     @GetMapping("/{id}")
//     public ResponseEntity<Equipement> getEquipementById(@PathVariable Long id) {
//         return equipementRepository.findById(id)
//                 .map(ResponseEntity::ok)
//                 .orElseGet(() -> ResponseEntity.notFound().build());
//     }

//     @PutMapping("/{id}/etat")
//     @PreAuthorize("hasRole('TECHNICIEN')")
//     public ResponseEntity<Equipement> updateEtat(
//             @PathVariable Long id,
//             @RequestBody Map<String, String> payload) {
        
//         return equipementRepository.findById(id)
//                 .map(equipement -> {
//                     equipement.setEtat(EtatEquipement.valueOf(payload.get("etat")));
//                     return ResponseEntity.ok(equipementRepository.save(equipement));
//                 })
//                 .orElseGet(() -> ResponseEntity.notFound().build());
//     }
// }
package ocp.maintenance.prediction.controller;

import ocp.maintenance.prediction.model.Equipement;
import ocp.maintenance.prediction.repository.EquipementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/equipements")
public class EquipementController {

    @Autowired
    private EquipementRepository equipementRepository;

    @PostMapping
    public Equipement createEquipement(@RequestBody Equipement equipement) {
        return equipementRepository.save(equipement);
    }

    @GetMapping
    public List<Equipement> getAllEquipements() {
        return equipementRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipement> getEquipementById(@PathVariable Long id) {
        Optional<Equipement> equipement = equipementRepository.findById(id);
        return equipement.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<Equipement> getEquipementByType(@PathVariable String type) {
        return equipementRepository.findByType(type)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Equipement> updateEquipement(@PathVariable Long id, @RequestBody Equipement equipementDetails) {
        return equipementRepository.findById(id).map(equipement -> {
            equipement.setType(equipementDetails.getType());
            equipement.setEtat(equipementDetails.getEtat());
            Equipement updatedEquipement = equipementRepository.save(equipement);
            return ResponseEntity.ok(updatedEquipement);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEquipement(@PathVariable Long id) {
        return equipementRepository.findById(id).map(equipement -> {
            equipementRepository.delete(equipement);
            return ResponseEntity.ok().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
