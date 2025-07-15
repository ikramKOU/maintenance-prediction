package ocp.maintenance.prediction.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ocp.maintenance.prediction.model.Employee;
import ocp.maintenance.prediction.model.RoleEmploye;
import ocp.maintenance.prediction.repository.CapteurRepository;
import ocp.maintenance.prediction.repository.EmployeRepository;
import ocp.maintenance.prediction.repository.EquipementRepository;
import ocp.maintenance.prediction.repository.InterventionRepository;

@RestController
@RequestMapping("/api/dashboard-summary")
public class DashboardController {

    private final EquipementRepository equipementRepository;
    private final CapteurRepository capteurRepository;
    private final InterventionRepository interventionRepository;
    private final EmployeRepository utilisateurRepository;

    public DashboardController(
            EquipementRepository equipementRepository,
            CapteurRepository capteurRepository,
            InterventionRepository interventionRepository,
            EmployeRepository utilisateurRepository
    ) {
        this.equipementRepository = equipementRepository;
        this.capteurRepository = capteurRepository;
        this.interventionRepository = interventionRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    @GetMapping
    public Map<String, Object> getDashboardSummary(JwtAuthenticationToken authToken) {
        Map<String, Object> result = new HashMap<>();

        result.put("equipmentCount", equipementRepository.count());
        result.put("sensorCount", capteurRepository.count());
        result.put("interventionCount", interventionRepository.count());

        String userRole = "UNKNOWN";
        String username = "";

        if (authToken != null) {
            // Le username est dans le claim "sub"
            username = authToken.getToken().getClaimAsString("sub");

            userRole = authToken.getAuthorities()
                    .stream()
                    .findFirst()
                    .map(a -> a.getAuthority())
                    .orElse("UNKNOWN");

            // Vérifier l'employé en BDD
            if (username != null && !username.isEmpty()) {
                Employee emp = utilisateurRepository.findByUsername(username);
                if (emp != null) {
                    if (emp.getRole() == RoleEmploye.ADMINISTRATEUR) {
                        result.put("userCount", utilisateurRepository.count());
                    }
                }
            }
        }

        result.put("role", userRole);
        result.put("username", username);

        return result;
    }
}
