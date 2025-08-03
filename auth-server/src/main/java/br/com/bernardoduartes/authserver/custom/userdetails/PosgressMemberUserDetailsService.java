package br.com.bernardoduartes.authserver.custom.userdetails;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class PosgressMemberUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("pguser".equals(username)) {
            return new CustomUserDetails("pguser", "{noop}pguser",
                    Collections.singleton(new SimpleGrantedAuthority("MEMBER_USER")), "user-456-pg");
        }
        throw new UsernameNotFoundException("Usuário do PostgreSQL não encontrado: " + username);
    }
}