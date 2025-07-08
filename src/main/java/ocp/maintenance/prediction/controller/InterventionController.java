package ocp.maintenance.prediction.controller;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ocp.maintenance.prediction.dto.InterventionDTO;
import ocp.maintenance.prediction.model.Alerte;
import ocp.maintenance.prediction.model.Employee;
import ocp.maintenance.prediction.model.Equipement;
import ocp.maintenance.prediction.model.Intervention;
import ocp.maintenance.prediction.model.StatutAlerte;
import ocp.maintenance.prediction.model.StatutIntervention;
import ocp.maintenance.prediction.repository.EmployeRepository;
import ocp.maintenance.prediction.service.AlerteLogService;
import ocp.maintenance.prediction.service.EquipementService;
import ocp.maintenance.prediction.service.InterventionService;
import ocp.maintenance.prediction.service.PdfService;


@RestController
@RequestMapping("/api/interventions")
// @CrossOrigin(origins = "*")
@CrossOrigin(origins = "http://localhost:5173")  

public class InterventionController {

    private final InterventionService interventionService;
    
    @Autowired
    private AlerteLogService alerteService;
 @Autowired
      private final EquipementService equipementService;
       @Autowired
    private final AlerteLogService employeeService;
    @Autowired
    private EmployeRepository empRepository;

    private final PdfService pdfService;

    public InterventionController(AlerteLogService alerteService, EmployeRepository empRepository, AlerteLogService employeeService, EquipementService equipementService, InterventionService interventionService, PdfService pdfService) {
        this.alerteService = alerteService;
        this.empRepository = empRepository;
        this.employeeService = employeeService;
        this.equipementService = equipementService;
        this.interventionService = interventionService;
        this.pdfService = pdfService;
    }




    @GetMapping
    public List<Intervention> getAll() {
        return interventionService.getAllInterventions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Intervention> getById(@PathVariable Long id) {
        return interventionService.getInterventionById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Intervention non trouvée"));
    }

    // @PostMapping
    // public ResponseEntity<Intervention> create(@RequestBody Intervention intervention) {
    //     Intervention saved = interventionService.saveIntervention(intervention);
    //     return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    // }

// @PostMapping
// public ResponseEntity<Intervention> create(@RequestBody Intervention intervention) {
//     Intervention saved = interventionService.saveIntervention(intervention);
//     return ResponseEntity.status(HttpStatus.CREATED).body(saved);
// }

// }

// @PostMapping(value = "/test", consumes = "application/json")
// public ResponseEntity<String> test(@RequestBody Map<String, Object> payload) {
//     return ResponseEntity.ok("Payload reçu: " + payload.toString());
// }
// @PostMapping(
//     value = "/create",
//     consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE },
//     produces = MediaType.APPLICATION_JSON_VALUE
// )
// public ResponseEntity<Intervention> create(@RequestBody Intervention intervention) {
//     if (intervention.getAlerte() != null && intervention.getAlerte().getId() != null) {
//          Alerte alerte = alerteService.findById(intervention.getAlerte().getId())
//                 .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Alerte non trouvée"));
//         intervention.setAlerte(alerte);

//         // mettre à jour l'alerte si nécessaire
//         alerte.setStatut(StatutAlerte.EN_COURS);
//         alerteService.saveAlerte(alerte);
//     }

//     Intervention saved = interventionService.saveIntervention(intervention);
//     return ResponseEntity.status(HttpStatus.CREATED).body(saved);
//  tania liltht}
// @PostMapping("/create")
// public ResponseEntity<Intervention> create(@RequestBody InterventionDTO dto) {
//     Intervention intervention = new Intervention();

//     // Parsing des dates
//     if (dto.getDateDebut() != null) {
//         intervention.setDateDebut(LocalDateTime.parse(dto.getDateDebut()));
//     }
//     if (dto.getDateFin() != null) {
//         intervention.setDateFin(LocalDateTime.parse(dto.getDateFin()));
//     }

//     // Détails
//     intervention.setDetails(dto.getDetails());

//     // Statut
//     if (dto.getStatut() != null) {
//         intervention.setStatut(StatutIntervention.valueOf(dto.getStatut()));
//     }

//     // Alerte
//     if (dto.getAlerte() != null) {
//         Alerte alerte = alerteService.findById(dto.getAlerte())
//             .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Alerte non trouvée"));
//         alerte.setStatut(StatutAlerte.EN_COURS);
//         alerteService.saveAlerte(alerte);
//         intervention.setAlerte(alerte);
//     }

//     // Employé
//     // if (dto.getEmploye() != null) {
//     //     Employee emp = employeeService.findById(dto.getEmploye())
//     //         .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employé non trouvé"));
//     //     intervention.setEmploye(emp);
//     // }

//     // Equipement
//     if (dto.getEquipement() != null) {
//         Equipement eq = equipementService.findById(dto.getEquipement())
//             .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Équipement non trouvé"));
//         intervention.setEquipement(eq);
//     }

//     Intervention saved = interventionService.saveIntervention(intervention);
//     return ResponseEntity.status(HttpStatus.CREATED).body(saved);
// }
@PostMapping("/create")
public ResponseEntity<Intervention> create(@RequestBody InterventionDTO dto) {
    Intervention intervention = new Intervention();

    intervention.setDateDebut(LocalDateTime.parse(dto.getDateDebut()));
    intervention.setDateFin(LocalDateTime.parse(dto.getDateFin()));
    intervention.setDetails(dto.getDetails());
    intervention.setStatut(StatutIntervention.valueOf(dto.getStatut()));

    // 🔁 Associer l'alerte
    if (dto.getAlerte() != null) {
        Alerte alerte = alerteService.findById(dto.getAlerte())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Alerte non trouvée"));
        alerte.setStatut(StatutAlerte.EN_COURS);
        alerteService.saveAlerte(alerte);
        intervention.setAlerte(alerte);
    }

    // ✅ Associer l'employé connecté via le token
//    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//         String username = auth.getName();
//        Employee emp = empRepository.findByUsername(username);
//        intervention.setEmploye(emp);

// // Sauvegarder l’intervention en base
// interventionRepository.save(intervention);
//     if (emp == null) {
//         throw new UsernameNotFoundException("Utilisateur non trouvé : " + username);
//     }
Authentication auth = SecurityContextHolder.getContext().getAuthentication();
String username = auth.getName();
Employee emp = empRepository.findByUsername(username);

if (emp == null) {
    throw new UsernameNotFoundException("Utilisateur non trouvé : " + username);
}

  intervention.setEmploye(emp);

    // Sauvegarder l’intervention en base
    interventionService.saveIntervention(intervention);
    // 🔁 Associer l’équipement
    if (dto.getEquipement() != null ) {
        Equipement eq = equipementService.findById(dto.getEquipement() )
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Equipement non trouvé"));
        intervention.setEquipement(eq);
    }

    Intervention saved = interventionService.saveIntervention(intervention);
    return ResponseEntity.status(HttpStatus.CREATED).body(saved);
}


//    @PutMapping("/{id}")
// public ResponseEntity<Intervention> update(@PathVariable Long id,
//                                            @RequestBody Intervention intervention) {
//     // Vérifier si l'intervention existe
//     Intervention existing = interventionService.findById(id)
//         .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Intervention non trouvée"));

//     // Si une alerte est liée, la récupérer proprement
//     if (intervention.getAlerte() != null && intervention.getAlerte().getId() != null) {
//         Alerte alerte = alerteService.findById(intervention.getAlerte().getId())
//             .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Alerte non trouvée"));
        
//         // Par exemple, on peut mettre à jour son statut si besoin
//         alerte.setStatut(StatutAlerte.EN_COURS);
//         alerteService.saveAlerte(alerte);

//         intervention.setAlerte(alerte);
//     }

//     // Mise à jour de l'intervention
//     Intervention updated = interventionService.updateIntervention(id, intervention);
//     return ResponseEntity.ok(updated);
// }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        interventionService.deleteIntervention(id);
    }

    @GetMapping("/employe/{employeId}")
    public List<Intervention> getByEmploye(@PathVariable Long employeId) {
        return interventionService.getByEmployeId(employeId);
    }

    @GetMapping("/equipement/{equipementId}")
    public List<Intervention> getByEquipement(@PathVariable Long equipementId) {
        return interventionService.getByEquipementId(equipementId);
    }

    @GetMapping("/entre-dates")
    public List<Intervention> getByDateRange(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return interventionService.getByDateDebutBetween(start, end);
    }

    @GetMapping("/en-cours")
    public List<Intervention> getEnCours() {
        return interventionService.getInterventionsEnCours();
    }

    @GetMapping("/statut/{statut}")
    public List<Intervention> getByStatut(@PathVariable StatutIntervention statut) {
        return interventionService.getByStatut(statut);
    }

    @GetMapping("/employe/{employeId}/statut/{statut}")
    public List<Intervention> getByEmployeAndStatut(@PathVariable Long employeId,
                                                    @PathVariable StatutIntervention statut) {
        return interventionService.getByEmployeIdAndStatut(employeId, statut);
    }
    

    @GetMapping("/stats")
    public Map<String, Long> getStats() {
        return interventionService.getStatistiquesGlobales();
    }
    @PutMapping("/{id}")
public ResponseEntity<Intervention> update(
        @PathVariable Long id,
        @RequestBody InterventionDTO dto) {

    Intervention existing = interventionService.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Intervention non trouvée"));

    // ✅ Mise à jour des champs
    existing.setDateDebut(LocalDateTime.parse(dto.getDateDebut()));
    existing.setDateFin(LocalDateTime.parse(dto.getDateFin()));
    existing.setDetails(dto.getDetails());
    existing.setStatut(StatutIntervention.valueOf(dto.getStatut()));

    // 🔁 Si intervention devient RESOLUE → changer le statut de l'alerte aussi
    if (existing.getStatut() == StatutIntervention.TERMINEE && existing.getAlerte() != null) {
        Alerte alerte = existing.getAlerte();
        alerte.setStatut(StatutAlerte.RESOLUE);
        alerteService.saveAlerte(alerte);
    }

    // ✅ Mettre à jour l'équipement si fourni
    if (dto.getEquipement() != null) {
        Equipement equipement = equipementService.findById(dto.getEquipement())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Équipement non trouvé"));
        existing.setEquipement(equipement);
    }

    // ✅ Rechercher l'employé connecté
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String username = auth.getName();
    Employee emp = empRepository.findByUsername(username);
    if (emp == null) {
        throw new UsernameNotFoundException("Utilisateur non trouvé : " + username);
    }
    existing.setEmploye(emp);

    // 🔄 Enregistrer
    Intervention updated = interventionService.saveIntervention(existing);
    return ResponseEntity.ok(updated);
}

    
//     @PutMapping("/{id}")
// public ResponseEntity<Intervention> update(
//         @PathVariable Long id,
//         @RequestBody Intervention intervention) {
    
//     Intervention existing = interventionService.findById(id)
//             .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Intervention non trouvée"));
    
//     // Si intervention passe au statut RESOLUE → mettre à jour l'alerte
//     if (intervention.getStatut() == StatutIntervention.RESOLUE) {
//         if (existing.getAlerte() != null) {
//             Alerte alerte = existing.getAlerte();
//             alerte.setStatut(StatutAlerte.RESOLUE);
//             alerteService.saveAlerte(alerte);
//         }
//     }
    
//     // mise à jour de l'intervention
//     Intervention updated = interventionService.updateIntervention(id, intervention);
//     return ResponseEntity.ok(updated);
// }



// @GetMapping("/{id}/rapport")
// public ResponseEntity<String> generateRapport(@PathVariable Long id) {
//     Intervention intervention = interventionService.findById(id)
//             .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Intervention non trouvée"));

//     String rapport = interventionService.creerRapport(intervention);
//     return ResponseEntity.ok(rapport);
// }

@GetMapping("/{id}/rapport-html")
public ResponseEntity<String> generateRapportHTML(@PathVariable Long id) {
    Intervention intervention = interventionService.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Intervention non trouvée"));

    String rapport = interventionService.creerRapportHTML(intervention);
    return ResponseEntity.ok()
           .header("Content-Type", "text/html")
           .body(rapport);
}


// @PostMapping("/{id}/rapport")
// public ResponseEntity<Intervention> saveRapport(@PathVariable Long id, @RequestBody String rapport) {
//     Intervention intervention = interventionService.findById(id)
//         .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Intervention non trouvée"));

//     intervention.setDetails(rapport);
//     interventionService.save(intervention);
//     return ResponseEntity.ok(intervention);
// }
@GetMapping("/{id}/rapport/pdf")
public ResponseEntity<InputStreamResource> generatePdfRapport(@PathVariable Long id) {
    Intervention intervention = interventionService.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Intervention non trouvée"));

    // Générer le HTML
    String htmlContent = interventionService.creerRapportHTML(intervention);
    
    // Convertir en PDF
    ByteArrayInputStream bis = pdfService.genererPdfDepuisHtml(htmlContent);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Disposition", "inline; filename=rapport_intervention_" + id + ".pdf");

    return ResponseEntity.ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(new InputStreamResource(bis));
}
// @GetMapping("/{id}/rapport/pdf")
// public ResponseEntity<InputStreamResource> generatePdfRapport(@PathVariable Long id) {
//     Intervention intervention = interventionService.findById(id)
//             .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Intervention non trouvée"));

//     ByteArrayInputStream bis = pdfService.genererPdfRapport(intervention);

//     HttpHeaders headers = new HttpHeaders();
//     headers.add("Content-Disposition", "inline; filename=rapport_intervention_" + id + ".pdf");

//     return ResponseEntity.ok()
//             .headers(headers)
//             .contentType(MediaType.APPLICATION_PDF)
//             .body(new InputStreamResource(bis));
// }

@GetMapping("/filtrer")
public List<Intervention> filterInterventions(
    @RequestParam(required = false) String statut,
    @RequestParam(required = false) Long employeId,
    @RequestParam(required = false) Long equipementId) {

    return interventionService.filtrerInterventions(statut, employeId, equipementId);
}

}
