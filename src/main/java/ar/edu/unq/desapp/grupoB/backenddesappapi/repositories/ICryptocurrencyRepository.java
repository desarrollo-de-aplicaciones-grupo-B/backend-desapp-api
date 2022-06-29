package ar.edu.unq.desapp.grupoB.backenddesappapi.repositories;

import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Cryptocurrency;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Configuration
@Repository
public interface ICryptocurrencyRepository extends CrudRepository<Cryptocurrency, Integer>{

    List<Cryptocurrency> findAll();

    @Query(value =  "SELECT c.id, c.crypto_name, c.nomenclature FROM cryptocurrency c WHERE c.nomenclature = :crypto_nomenclature"
            , nativeQuery = true)
    Cryptocurrency getCryptoByName(@Param("crypto_nomenclature") String crypto_nomenclature);
}
