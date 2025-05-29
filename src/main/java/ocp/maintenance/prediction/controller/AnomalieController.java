package ocp.maintenance.prediction.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ocp.maintenance.prediction.model.Anomalie;
import ocp.maintenance.prediction.service.AnomalieService;

@RestController
@RequestMapping("/api/anomalies")
public class AnomalieController {

    private final AnomalieService anomalieService;

    public AnomalieController(AnomalieService anomalieService) {
        this.anomalieService = anomalieService;
    }

    // @PostMapping
    // @PreAuthorize("hasRole('OPERATEUR')")
    // public ResponseEntity<Anomalie> createAnomalie(@RequestBody Anomalie anomalie) {
    //     return ResponseEntity.status(HttpStatus.CREATED)
    //             .body(anomalieService.createAnomalie(anomalie));
    // }
}
