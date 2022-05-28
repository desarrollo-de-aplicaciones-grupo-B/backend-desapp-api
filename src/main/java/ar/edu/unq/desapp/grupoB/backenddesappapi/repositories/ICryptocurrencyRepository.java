package ar.edu.unq.desapp.grupoB.backenddesappapi.repositories;

import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Cryptocurrency;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Configuration
@Repository
public interface ICryptocurrencyRepository extends CrudRepository<Cryptocurrency, Integer>{

    List<Cryptocurrency> findAll();
}
