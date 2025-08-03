package br.com.bernardoduartes.authserver;

import br.com.bernardoduartes.authserver.custom.userdetails.DefaultUserDetailsService;
import br.com.bernardoduartes.authserver.custom.userdetails.MysqlUserDetailsService;
import br.com.bernardoduartes.authserver.custom.userdetails.PosgressMemberUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityFilterConfig {

    @Bean
    @Order(1)
    SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class).oidc(withDefaults());

        http.exceptionHandling(exceptions -> exceptions.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login")))
                .oauth2ResourceServer(conf -> conf.jwt(withDefaults()));

        return http.build();
    }

    @Bean
    @Order(2)
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager)
            throws Exception {
        return http
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
                .authenticationManager(authenticationManager)
                .formLogin(withDefaults())
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            DefaultUserDetailsService defaultService,
            MysqlUserDetailsService mysqlService,
            PosgressMemberUserDetailsService postgresService) {

        DaoAuthenticationProvider defaultProvider = new DaoAuthenticationProvider();
        defaultProvider.setUserDetailsService(defaultService);

        DaoAuthenticationProvider mysqlProvider = new DaoAuthenticationProvider();
        mysqlProvider.setUserDetailsService(mysqlService);

        DaoAuthenticationProvider postgresProvider = new DaoAuthenticationProvider();
        postgresProvider.setUserDetailsService(postgresService);

        return new ProviderManager(List.of(
                mysqlProvider,
                postgresProvider,
                defaultProvider // Default last, so other specific ones are preferred
        ));
    }

}