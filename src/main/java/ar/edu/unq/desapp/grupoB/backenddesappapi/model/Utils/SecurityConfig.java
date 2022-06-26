package ar.edu.unq.desapp.grupoB.backenddesappapi.model.Utils;


import ar.edu.unq.desapp.grupoB.backenddesappapi.repositories.IUserRepository;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private IUserRepository userRepo;

    public SecurityConfig(IUserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //    auth.userDetailsService(username -> userRepo
        //            .findByUsername(username));
        //            .orElseThrow(
        //                    () -> new UsernameNotFoundException(
        //                            format("User: %s, not found", username)
        //                    )
        //            ));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // TODO configure web security
    }
}