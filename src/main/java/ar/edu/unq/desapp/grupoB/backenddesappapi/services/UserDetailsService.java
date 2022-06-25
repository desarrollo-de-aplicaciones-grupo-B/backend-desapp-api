package ar.edu.unq.desapp.grupoB.backenddesappapi.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailsService { // interface that loads user-specific data
    UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException;
}
