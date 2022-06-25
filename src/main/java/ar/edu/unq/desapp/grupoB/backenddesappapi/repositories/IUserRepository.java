package ar.edu.unq.desapp.grupoB.backenddesappapi.repositories;

import ar.edu.unq.desapp.grupoB.backenddesappapi.model.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Configuration
@Repository
public interface IUserRepository extends CrudRepository<User, Integer> {
    Optional<User> findById(Integer id);

    List<User> findAll();

    User findUserByName(String username);

}
