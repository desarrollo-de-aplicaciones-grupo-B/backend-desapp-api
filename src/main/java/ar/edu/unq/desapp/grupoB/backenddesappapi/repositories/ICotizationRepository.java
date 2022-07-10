package ar.edu.unq.desapp.grupoB.backenddesappapi.repositories;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Cotization;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Configuration
@Repository
public interface ICotizationRepository extends CrudRepository<Cotization, Integer> {

    Optional<Cotization> findById(Integer id);

    List<Cotization> findAll();

    @Query(value =  "SELECT coti.id, coti.date_cotization, coti.price_cotization, coti.crypto_nomenclature " +
            "FROM cotizations coti " +
            "INNER JOIN cryptocurrency crypto ON crypto.nomenclature = coti.crypto_nomenclature AND crypto.nomenclature = :crypto_name " +
            "WHERE date_cotization BETWEEN NOW()- INTERVAL '1 DAY' AND NOW()"
            , nativeQuery = true)
    List<Cotization> findLast24HoursCotizations( @Param("crypto_name") String crypto_name );

    @Query(value = "SELECT coti.id, coti.date_cotization, coti.price_cotization, coti.crypto_nomenclature "+
            "FROM cotizations coti " +
            "WHERE coti.crypto_nomenclature = :crypto_nomenclature " +
            "ORDER BY coti.date_cotization desc" +
            "LIMIT 1",
            nativeQuery = true)
    Object findLastCotization( @Param("crypto_nomenclature") String crypto_nomenclature);
}
