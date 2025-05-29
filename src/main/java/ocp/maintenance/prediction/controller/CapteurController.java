package ocp.maintenance.prediction.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ocp.maintenance.prediction.service.CapteurDataListener;

@RestController
@RequestMapping("/api/capteurs")
public class CapteurController {

    private final CapteurDataListener capteurDataListener;

    public CapteurController(CapteurDataListener capteurDataListener) {
        this.capteurDataListener = capteurDataListener;
    }

    // @PostMapping("/webhook")
    // public ResponseEntity<Void> handleCapteurData(@RequestBody CapteurData data) {
    //     capteurDataListener.processCapteurData(data);
    //     return ResponseEntity.ok().build();
    // }
}

// // DTO pour les données capteurs
// public class CapteurData {
//     private Long capteurId;
//     private float value;
    
//     // Getters et setters
// }
