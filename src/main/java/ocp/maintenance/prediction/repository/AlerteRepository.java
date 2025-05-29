package ocp.maintenance.prediction.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ocp.maintenance.prediction.model.Alerte;
import ocp.maintenance.prediction.model.StatutAlerte;




public interface AlerteRepository extends JpaRepository<Alerte, Long> {
    List<Alerte> findByStatut(StatutAlerte statut);
    List<Alerte> findByEquipementId(Long equipementId);
    List<Alerte> findByEmployeId(Long employeId);
}