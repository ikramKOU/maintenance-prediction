package ocp.maintenance.prediction.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ocp.maintenance.prediction.model.Capteur;
import ocp.maintenance.prediction.repository.CapteurRepository;

@Service
public class CapteurService {

    private final CapteurRepository capteurRepository;

    public CapteurService(CapteurRepository capteurRepository) {
        this.capteurRepository = capteurRepository;
    }

    public Capteur save(Capteur capteur) {
        return capteurRepository.save(capteur);
    }

    public List<Capteur> findAll() {
        return capteurRepository.findAll();
    }

    public Optional<Capteur> findById(Long id) {
        return capteurRepository.findById(id);
    }

    public List<Capteur> findByEquipementId(Long equipementId) {
        return capteurRepository.findByEquipementId(equipementId);
    }

    public void deleteById(Long id) {
        capteurRepository.deleteById(id);
    }
}
