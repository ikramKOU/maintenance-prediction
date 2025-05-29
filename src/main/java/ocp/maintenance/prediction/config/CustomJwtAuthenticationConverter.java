package ocp.maintenance.prediction.config;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

public class CustomJwtAuthenticationConverter  extends JwtAuthenticationConverter{

     public CustomJwtAuthenticationConverter() {
        // Injecte le converter custom pour extraire les rôles à partir de la claim "role"
        setJwtGrantedAuthoritiesConverter(new Converter<Jwt, Collection<GrantedAuthority>>() {
            @Override
            public Collection<GrantedAuthority> convert(Jwt jwt) {
                String role = jwt.getClaimAsString("role");
                if (role == null) {
                    return Collections.emptyList();
                }
                return List.of(new SimpleGrantedAuthority("ROLE_" + role));
            }
        });
    }
    
}
