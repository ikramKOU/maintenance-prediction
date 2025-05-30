package ocp.maintenance.prediction.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ocp.maintenance.prediction.model.Equipement;
import ocp.maintenance.prediction.model.EtatEquipement;



public interface EquipementRepository extends JpaRepository<Equipement, Long> {
    List<Equipement> findByEtat(EtatEquipement etat);
    Optional<Equipement> findByType(String type);  
}