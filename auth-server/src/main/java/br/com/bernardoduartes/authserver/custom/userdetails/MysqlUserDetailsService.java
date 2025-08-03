package br.com.bernardoduartes.authserver.custom.userdetails;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class MysqlUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("mysql".equals(username)) {
            return new CustomUserDetails("mysql", "{noop}mysql",
                    Collections.singleton(new SimpleGrantedAuthority("DB_USER")), "user-123-mysql");
        }
        throw new UsernameNotFoundException("Usuário do MySQL não encontrado: " + username);
    }
}