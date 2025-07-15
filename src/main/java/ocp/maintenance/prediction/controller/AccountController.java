package ocp.maintenance.prediction.controller;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import ocp.maintenance.prediction.config.TokenBlacklistService;
import ocp.maintenance.prediction.dto.LoginDto;
import ocp.maintenance.prediction.model.Employee;
import ocp.maintenance.prediction.repository.EmployeRepository;

@RestController
@RequestMapping("/api/auth")
public class AccountController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Value("${security.jwt.secret-key}")
    private String jwtSecretKey;

    @Value("${security.jwt.issuer}")
    private String jwtIssuer;

    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private EmployeRepository employeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

       private final TokenBlacklistService tokenBlacklistService;

    // Modifiez le constructeur pour inclure TokenBlacklistService
    @Autowired
    public AccountController(AuthenticationManager authenticationManager,
                           EmployeRepository employeRepository,
                           PasswordEncoder passwordEncoder,
                           JwtEncoder jwtEncoder,
                           TokenBlacklistService tokenBlacklistService) {
        this.authenticationManager = authenticationManager;
        this.employeRepository = employeRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
        this.tokenBlacklistService = tokenBlacklistService;
    }

    @GetMapping("/profil")
    public ResponseEntity<?> profile(Authentication auth) {
        var response = new HashMap<String, Object>();
        response.put("username", auth.getName());
        response.put("authorities", auth.getAuthorities());

        var employee = employeRepository.findByUsername(auth.getName());
        response.put("employee", employee);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        Employee employee = employeRepository.findByUsername(loginDto.getUsername());

        if (employee == null || !passwordEncoder.matches(loginDto.getPassword(), employee.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("error", "Invalid username or password"));
        }

        String token = createJwtToken(employee);

        if (token == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Token generation failed"));
        }

        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }

     @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            
            // Ajoute le token à la blacklist
            tokenBlacklistService.addToBlacklist(token);
            
            // Réponse avec en-tête de nettoyage client
            return ResponseEntity.ok()
                .header("Clear-Site-Data", "\"cache\", \"cookies\", \"storage\"")
                .body(Collections.singletonMap("message", "Déconnexion réussie"));
        }
        return ResponseEntity.badRequest()
            .body(Collections.singletonMap("error", "Token manquant ou mal formaté"));
    }
// @PostMapping("/login")
// public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
//     Optional<Employee> optionalEmployee = employeRepository.findByUsername(loginDto.getUsername());

//     if (optionalEmployee.isEmpty() || !passwordEncoder.matches(loginDto.getPassword(), optionalEmployee.get().getPassword())) {
//         return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                 .body(Collections.singletonMap("error", "Invalid username or password"));
//     }

//     Employee employee = optionalEmployee.get();
//     String token = createJwtToken(employee);

//     if (token == null) {
//         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                 .body(Collections.singletonMap("error", "Token generation failed"));
//     }

//     return ResponseEntity.ok(Collections.singletonMap("token", token));
// }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Employee employee) {
        if (employeRepository.findByUsername(employee.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Collections.singletonMap("error", "Username already exists"));
        }

        Employee newEmployee = new Employee();
        newEmployee.setUsername(employee.getUsername());
        newEmployee.setNom(employee.getNom());
        newEmployee.setPassword(passwordEncoder.encode(employee.getPassword()));
        newEmployee.setRole(employee.getRole());
        newEmployee.setEmail(employee.getEmail());
        newEmployee.setCreatedAt(Date.from(Instant.now()));
        newEmployee.setPhone(employee.getPhone());

        Employee savedEmployee = employeRepository.save(newEmployee);

        String token = createJwtToken(savedEmployee);

        if (token == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Token generation failed"));
        }

        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }

    public String createJwtToken(Employee emp) {
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(jwtIssuer)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(24 * 3600))
                .subject(emp.getUsername())
                .claim("role", emp.getRole())
                .build();

        JwsHeader jwsHeader = JwsHeader.with(MacAlgorithm.HS256).build();
        JwtEncoderParameters parameters = JwtEncoderParameters.from(jwsHeader, claims);
        Jwt jwt = jwtEncoder.encode(parameters);

        return jwt.getTokenValue();
    }
}
