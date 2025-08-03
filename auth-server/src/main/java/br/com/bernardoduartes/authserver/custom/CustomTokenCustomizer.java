package br.com.bernardoduartes.authserver.custom;

import java.util.Set;
import java.util.stream.Collectors;

import br.com.bernardoduartes.authserver.custom.userdetails.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.stereotype.Component;

@Component
public class CustomTokenCustomizer implements OAuth2TokenCustomizer<JwtEncodingContext> {

    @Override
    public void customize(JwtEncodingContext context) {
        if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
            Authentication principal = context.getPrincipal();
            Set<String> authorities = principal.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet());

            if (principal.getPrincipal() instanceof CustomUserDetails userDetails) {
                context.getClaims().claim("userId", userDetails.getUserId());
                authorities.add("DB_USER");
            }

            context.getClaims().claim("authorities", authorities);
        }
    }
}