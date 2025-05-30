package ocp.maintenance.prediction.config;

import java.nio.charset.StandardCharsets;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {



     @Value("${security.jwt.secret-key}")
    private String jwtSecretKey;

    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    //     return http
    //         .csrf(csrf -> csrf.disable())
            
    //         .authorizeHttpRequests(auth -> auth
    //         .requestMatchers("/api/auth/register","/api/auth/login").permitAll()
    //         .anyRequest().authenticated())
    //         .oauth2ResourceServer(oauth2 -> oauth2.jwt(withDefaults()))
    //                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    //         .build();
    // }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationConverter jwtAuthenticationConverter = new CustomJwtAuthenticationConverter();

        return http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/register", "/api/auth/login").permitAll()
                .requestMatchers( "/api/equipements/**","/api/employees/**").hasRole("ADMINISTRATEUR")
                .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt
                    .jwtAuthenticationConverter(jwtAuthenticationConverter)
                )
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Bean
    public JwtDecoder jwtDecoder() {
        byte[] secretKeyBytes = jwtSecretKey.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyBytes, "HmacSHA256");
        return NimbusJwtDecoder.withSecretKey(secretKeySpec).build();
    }
      

     @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService,
                                                       PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(new BCryptPasswordEncoder());

        return new ProviderManager(authProvider);
    }

    
}
