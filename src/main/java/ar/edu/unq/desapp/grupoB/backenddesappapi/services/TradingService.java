package ar.edu.unq.desapp.grupoB.backenddesappapi.services;

import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Trading;
import ar.edu.unq.desapp.grupoB.backenddesappapi.repositories.ITradingRepository;
import ar.edu.unq.desapp.grupoB.backenddesappapi.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TradingService {

    @Autowired
    private ITradingRepository tradingRepository;

    @Autowired
    private IUserRepository userRepository;

    @Transactional
    public Trading save(Trading trading){
        return this.tradingRepository.save(trading);
    }

    @Transactional
    public Trading findByID(Integer id) {
        return this.tradingRepository.findById(id).get();
    }

    @Transactional
    public List<Trading> findAll() {
        return this.tradingRepository.findAll();
    }

    @Transactional
    public void deleteById(Integer id) {
        this.tradingRepository.deleteById(id);
    }

    @Transactional
    public Trading updateTrading(Trading trading){ return (Trading) this.tradingRepository.save(trading);}


}
