package ar.edu.unq.desapp.grupoB.backenddesappapi.repositories;

import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Trading;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Configuration
@Repository
public interface ITradingRepository extends CrudRepository<Trading, Integer> {
    Optional<Trading> findById(Integer id);

    List<Trading> findAll();
}
