package ocp.maintenance.prediction.service;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import ocp.maintenance.prediction.model.Capteur;
import ocp.maintenance.prediction.model.Anomalie;

import ocp.maintenance.prediction.repository.CapteurRepository;

@Service
public class CapteurDataListener {

    private final AnomalieService anomalieService;
    private final CapteurRepository capteurRepository;

    public CapteurDataListener(AnomalieService anomalieService,
                             CapteurRepository capteurRepository) {
        this.anomalieService = anomalieService;
        this.capteurRepository = capteurRepository;
    }

    // Méthode appelée lorsqu'une nouvelle valeur de capteur arrive
    // @Transactional
    // public void processCapteurData(CapteurData data) {
    //     Capteur capteur = capteurRepository.findById(data.getCapteurId())
    //             .orElseThrow(() -> new EntityNotFoundException("Capteur non trouvé"));
        
    //     // Détection d'anomalie basique (exemple)
    //     if (isAnomalieDetected(capteur, data.getValue())) {
    //         Anomalie anomalie = new Anomalie();
    //         anomalie.setType("Valeur anormale");
    //         anomalie.setDescription("Valeur détectée: " + data.getValue() + " (seuil: " + capteur.getSeuil() + ")");
    //         anomalie.setGravite(determineGravite(data.getValue(), capteur.getSeuil()));
    //         anomalie.setCapteur(capteur);
            
    //         anomalieService.createAnomalie(anomalie);
    //     }
    // }

    // private boolean isAnomalieDetected(Capteur capteur, float value) {
    //     // Logique de détection d'anomalie
    //     return value > capteur.getSeuil();
    // }

    // private GraviteAnomalie determineGravite(float value, float seuil) {
    //     float deviation = Math.abs(value - seuil) / seuil;
    //     if (deviation > 0.5) return GraviteAnomalie.CRITIQUE;
    //     if (deviation > 0.3) return GraviteAnomalie.MAJEUR;
    //     return GraviteAnomalie.MINEUR;
    // }

    // private static class CapteurData {

    //     public CapteurData() {
    //     }
    // }
}