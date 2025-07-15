package ocp.maintenance.prediction.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ocp.maintenance.prediction.model.Employee;
import ocp.maintenance.prediction.repository.EmployeRepository;

@RestController
@RequestMapping("/api/user-role")
public class UserRoleController {

    private final EmployeRepository employeRepository;

    public UserRoleController(EmployeRepository employeRepository) {
        this.employeRepository = employeRepository;
    }

    @GetMapping
    public Map<String, String> getUserRole() {
        // ⚠ Ici c'est une simulation → récupère arbitrairement le premier utilisateur
        Employee employe = employeRepository.findAll().stream().findFirst().orElse(null);

        Map<String, String> result = new HashMap<>();
        if (employe != null && employe.getRole() != null) {
            result.put("role", employe.getRole().name());
        } else {
            result.put("role", "UNKNOWN");
        }
        return result;
    }
}
