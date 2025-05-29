package ocp.maintenance.prediction.config;


import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.beans.factory.annotation.Value;


import com.nimbusds.jose.jwk.source.ImmutableSecret;

import jakarta.annotation.PostConstruct;

@Configuration
public class JwtConfig {

    @Value("${security.jwt.secret-key}")
    private String jwtSecretKey;
    @PostConstruct
public void init() {
    if (jwtSecretKey == null || jwtSecretKey.isEmpty()) {
        System.err.println("La clé jwt.secret n'est pas configurée ou est vide !");
    } else {
        System.out.println("Clé jwt.secret chargée, longueur = " + jwtSecretKey.length());
    }
}
 
      @Bean
public JwtEncoder jwtEncoder() {
    byte[] secretBytes = jwtSecretKey.getBytes(StandardCharsets.UTF_8);
    SecretKey key = new SecretKeySpec(secretBytes, "HmacSHA256");
    return new NimbusJwtEncoder(new ImmutableSecret<>(key));
}
}

