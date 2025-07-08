package ocp.maintenance.prediction.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ocp.maintenance.prediction.model.Intervention;
import ocp.maintenance.prediction.model.StatutIntervention;

public interface InterventionRepository extends JpaRepository<Intervention, Long> {



    //  List<Intervention> findByStatut(StatutIntervention statut);

    // List<Intervention> findByEmployeId(Long employeId);

    // List<Intervention> findByEquipementId(Long equipementId);

    // List<Intervention> findByDateDebutBetween(LocalDateTime start, LocalDateTime end);

    // List<Intervention> findByDateFinIsNull();

   

    // List<Intervention> findByEmployeIdAndStatut(Long employeId, StatutIntervention statut);

    // // Pour les statistiques globales
    // long countByStatut(StatutIntervention statut);

    // // Pour lier intervention à une alerte précise
    // List<Intervention> findByAlerte_Id(Long alerteId);








    // List<Intervention> findByEmployeId(Long id);

    // List<Intervention> findByEquipementId(Long id);

    // List<Intervention> findByDateDebutBetween(LocalDateTime start, LocalDateTime end);

    // List<Intervention> findByStatut(StatutIntervention statut);

    // List<Intervention> findByEmployeIdAndStatut(Long id, StatutIntervention statut);

    // long countByStatut(StatutIntervention statut);

    List<Intervention> findByEmployeId(Long employeId);
    List<Intervention> findByEquipementId(Long equipementId);
    List<Intervention> findByDateDebutBetween(LocalDateTime start, LocalDateTime end);
    List<Intervention> findByStatut(StatutIntervention statut);
    List<Intervention> findByEmployeIdAndStatut(Long employeId, StatutIntervention statut);

     long countByStatut(StatutIntervention statut);

    // (optionnel) Lier intervention à une alerte
    List<Intervention> findByAlerte_Id(Long alerteId);

    List<Intervention> findByDateFinIsNull();

}
