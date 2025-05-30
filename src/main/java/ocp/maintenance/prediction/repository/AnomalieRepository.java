package ocp.maintenance.prediction.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ocp.maintenance.prediction.model.Anomalie;

public interface AnomalieRepository extends JpaRepository<Anomalie, Long>{


    

}


