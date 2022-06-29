package ar.edu.unq.desapp.grupoB.backenddesappapi.services;

import java.util.ArrayList;

import ar.edu.unq.desapp.grupoB.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoB.backenddesappapi.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserRepository iUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User userFounded = iUserRepository.findUsersByEmail(email);
        if( userFounded == null){
            throw new UsernameNotFoundException("User not found");
        }
        return null;
        //return (UserDetails) new User(userFounded.getEmail(),userFounded.getPassword());
    }
}