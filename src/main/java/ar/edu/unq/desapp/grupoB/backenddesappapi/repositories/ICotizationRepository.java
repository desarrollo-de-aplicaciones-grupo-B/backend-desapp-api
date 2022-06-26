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

    @Query(value =  "SELECT * " +
            "FROM cotizations c" +
            "WHERE date_cotization BETWEEN GETDATE()-1 AND GETDATE() " +
            "AND c.id = :id", nativeQuery = true)
    List<Cotization> findLast24HoursCotizations( @Param("id") Integer id );
}
