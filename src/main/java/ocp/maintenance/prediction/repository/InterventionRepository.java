package ocp.maintenance.prediction.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ocp.maintenance.prediction.model.Intervention;
import ocp.maintenance.prediction.model.StatutIntervention;

public interface InterventionRepository extends JpaRepository<Intervention, Long> {
    List<Intervention> findByEmployeId(Long employeId);
    List<Intervention> findByEquipementId(Long equipementId);
    List<Intervention> findByDateDebutBetween(LocalDateTime start, LocalDateTime end);
    List<Intervention> findByDateFinIsNull();
    List<Intervention> findByStatut(StatutIntervention statut);

    List<Intervention> findByEmployeIdAndStatut(Long employeId, StatutIntervention statut);


}
