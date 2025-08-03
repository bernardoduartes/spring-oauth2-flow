package br.com.bernardoduartes.authserver.custom.userdetails;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.equals("dbuser")) {
            return org.springframework.security.core.userdetails.User
                    .withUsername("dbuser")
                    .password("{noop}dbuser")
                    .roles("DB_USER")
                    .build();
        } else if (username.equals("apiuser")) {
            return org.springframework.security.core.userdetails.User
                    .withUsername("apiuser")
                    .password("{noop}apiuser")
                    .roles("API_USER")
                    .build();
        }
        throw new UsernameNotFoundException("Usuário não encontrado: " + username);
    }
}
