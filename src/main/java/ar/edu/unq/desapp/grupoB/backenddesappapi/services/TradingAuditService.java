package ar.edu.unq.desapp.grupoB.backenddesappapi.services;

import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Trading;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.TradingAudit;
import ar.edu.unq.desapp.grupoB.backenddesappapi.repositories.ITradingAuditRepository;
import ar.edu.unq.desapp.grupoB.backenddesappapi.repositories.ITradingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TradingAuditService {

    @Autowired
    private ITradingAuditRepository tradingAuditRepository;

    @Transactional
    public TradingAudit save(TradingAudit trading){
        return this.tradingAuditRepository.save(trading);
    }

    @Transactional
    public TradingAudit findByID(Integer id) {
        return this.tradingAuditRepository.findById(id).get();
    }

    @Transactional
    public List<TradingAudit> findAll() {
        return this.tradingAuditRepository.findAll();
    }

    @Transactional
    public void deleteById(Integer id) {
        this.tradingAuditRepository.deleteById(id);
    }

    @Transactional
    public TradingAudit updateTrading(TradingAudit trading){ return (TradingAudit) this.tradingAuditRepository.save(trading);}

}
