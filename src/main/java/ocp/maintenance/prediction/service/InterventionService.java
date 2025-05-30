package ocp.maintenance.prediction.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ocp.maintenance.prediction.model.Intervention;
import ocp.maintenance.prediction.model.StatutIntervention;
import ocp.maintenance.prediction.repository.InterventionRepository;

@Service
public class InterventionService {

    private final InterventionRepository interventionRepository;

    public InterventionService(InterventionRepository interventionRepository) {
        this.interventionRepository = interventionRepository;
    }

    public List<Intervention> getAllInterventions() {
        return interventionRepository.findAll();
    }

    public Optional<Intervention> getInterventionById(Long id) {
        return interventionRepository.findById(id);
    }

    public Intervention saveIntervention(Intervention intervention) {
        return interventionRepository.save(intervention);
    }

    public void deleteIntervention(Long id) {
        interventionRepository.deleteById(id);
    }

    public List<Intervention> getByEmployeId(Long employeId) {
        return interventionRepository.findByEmployeId(employeId);
    }

    public List<Intervention> getByEquipementId(Long equipementId) {
        return interventionRepository.findByEquipementId(equipementId);
    }

    public List<Intervention> getByDateDebutBetween(LocalDateTime start, LocalDateTime end) {
        return interventionRepository.findByDateDebutBetween(start, end);
    }

    public List<Intervention> getInterventionsEnCours() {
        return interventionRepository.findByDateFinIsNull();
    }

    public List<Intervention> getByStatut(StatutIntervention statut) {
        return interventionRepository.findByStatut(statut);
    }

    public List<Intervention> getByEmployeIdAndStatut(Long employeId, StatutIntervention statut) {
        return interventionRepository.findByEmployeIdAndStatut(employeId, statut);
    }
}
