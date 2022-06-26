package ar.edu.unq.desapp.grupoB.backenddesappapi.repositories;

import ar.edu.unq.desapp.grupoB.backenddesappapi.model.DTO.TradingUserDTO;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Trading;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Configuration
@Repository
public interface ITradingRepository extends CrudRepository<Trading, Integer> {
    Optional<Trading> findById(Integer id);

    @Query(value = "SELECT creationDate, c.crypto_name, u.user_name, u.user_last_name, t.cryptoAmount, t.cotization, t.operationAmount,  " +
            "FROM trading t" +
            "INNER JOIN cryptocurrency c ON t.cryptoId = c.id " +
            "INNER JOIN user_table u u.id = t.userId" +
            "AND u.id = :id", nativeQuery = true)
    List<TradingUserDTO> getAllUserTradings( @Param("id") Integer id );
}
