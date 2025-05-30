package ocp.maintenance.prediction.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import ocp.maintenance.prediction.model.Intervention;
import ocp.maintenance.prediction.model.StatutIntervention;
import ocp.maintenance.prediction.service.InterventionService;

@RestController
@RequestMapping("/api/interventions")
@CrossOrigin(origins = "*")
public class InterventionController {

    private final InterventionService interventionService;

    public InterventionController(InterventionService interventionService) {
        this.interventionService = interventionService;
    }

    @GetMapping
    public List<Intervention> getAll() {
        return interventionService.getAllInterventions();
    }

    @GetMapping("/{id}")
    public Intervention getById(@PathVariable Long id) {
        return interventionService.getInterventionById(id).orElse(null);
    }

    @PostMapping
    public Intervention create(@RequestBody Intervention intervention) {
        return interventionService.saveIntervention(intervention);
    }

    @DeleteMapping("/{id}")
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
        @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
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
    public List<Intervention> getByEmployeAndStatut(@PathVariable Long employeId, @PathVariable StatutIntervention statut) {
        return interventionService.getByEmployeIdAndStatut(employeId, statut);
    }
}
