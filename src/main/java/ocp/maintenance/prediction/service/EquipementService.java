package ocp.maintenance.prediction.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ocp.maintenance.prediction.model.Equipement;
import ocp.maintenance.prediction.model.EtatEquipement;
import ocp.maintenance.prediction.repository.EquipementRepository;



@Service
public class EquipementService {

    private final EquipementRepository equipementRepository;

    @Autowired
    public EquipementService(EquipementRepository equipementRepository) {
        this.equipementRepository = equipementRepository;
    }

    public Equipement save(Equipement equipement) {
        return equipementRepository.save(equipement);
    }

    public List<Equipement> findAll() {
        return equipementRepository.findAll();
    }

      public Optional<Equipement> findById(Long id) {
        return equipementRepository.findById(id);
    }

    public Optional<Equipement> findByType(String type) {
        return equipementRepository.findByType(type);
    }

    public Optional<Equipement> findByName(String name) {
        return equipementRepository.findByName(name);
    }

    public List<Equipement> findByEtat(EtatEquipement etat) {
        return equipementRepository.findByEtat(etat);
    }

    public void deleteById(Long id) {
        equipementRepository.deleteById(id);
    }

    public Equipement updateEquipement(Long id, Equipement newEquipement) {
        return equipementRepository.findById(id)
                .map(equipement -> {
                    equipement.setType(newEquipement.getType());
                    equipement.setEtat(newEquipement.getEtat());
                    return equipementRepository.save(equipement);
                })
                .orElseThrow(() -> new RuntimeException("Équipement non trouvé avec l'id : " + id));
    }
}
