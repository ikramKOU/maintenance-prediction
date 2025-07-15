package ocp.maintenance.prediction.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ocp.maintenance.prediction.model.Prediction;

@Repository
public interface PredictionRepository extends JpaRepository<Prediction, Long> {
    List<Prediction> findTop50ByOrderByTimestampDesc();
}