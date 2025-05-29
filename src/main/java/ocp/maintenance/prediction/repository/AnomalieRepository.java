package ocp.maintenance.prediction.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ocp.maintenance.prediction.model.Anomalie;
import ocp.maintenance.prediction.model.GraviteAnomalie;

public interface AnomalieRepository extends JpaRepository<Anomalie, Long> {
    List<Anomalie> findByGravite(GraviteAnomalie gravite);
    List<Anomalie> findByCapteurId(Long capteurId);
}