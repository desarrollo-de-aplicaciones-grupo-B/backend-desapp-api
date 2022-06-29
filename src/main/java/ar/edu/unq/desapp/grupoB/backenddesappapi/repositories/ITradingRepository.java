package ar.edu.unq.desapp.grupoB.backenddesappapi.repositories;

import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Trading;
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

    @Query(value = "SELECT creation_date, c.crypto_name, t.crypto_amount, t.cotization, t.operation_amount, concat(u.user_name,  ' ',  u.user_last_name), u.successful_operations, case when u.successful_operations = 0 then cast(0.0 as double precision) else u.reputation_points / u.successful_operations end " +
            "FROM trading t " +
            "INNER JOIN cryptocurrency c ON t.crypto_id = c.id " +
            "INNER JOIN user_table u ON u.id = t.seller_id " +
            "AND u.id = :id", nativeQuery = true)
    List<Object[]> getAllUserTradings(@Param("id") Integer id );
}
