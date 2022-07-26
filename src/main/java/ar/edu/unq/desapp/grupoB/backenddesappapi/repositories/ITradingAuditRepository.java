package ar.edu.unq.desapp.grupoB.backenddesappapi.repositories;

import ar.edu.unq.desapp.grupoB.backenddesappapi.model.TradingAudit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITradingAuditRepository extends CrudRepository<TradingAudit, Integer> {

    Optional<TradingAudit> findById(Integer id);

    List<TradingAudit> findAll();

}
