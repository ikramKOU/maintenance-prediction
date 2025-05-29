package ocp.maintenance.prediction.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ocp.maintenance.prediction.model.Capteur;




public interface CapteurRepository extends JpaRepository<Capteur, Long> {
    List<Capteur> findByEquipementId(Long equipementId);
    List<Capteur> findByTimestampAfter(LocalDateTime timestamp);
}