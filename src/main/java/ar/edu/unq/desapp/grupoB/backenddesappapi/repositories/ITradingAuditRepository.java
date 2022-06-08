package ar.edu.unq.desapp.grupoB.backenddesappapi.repositories;

import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Trading;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.TradingAudit;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ITradingAuditRepository extends CrudRepository<TradingAudit, Integer> {

    Optional<TradingAudit> findById(Integer id);

    List<TradingAudit> findAll();

}
