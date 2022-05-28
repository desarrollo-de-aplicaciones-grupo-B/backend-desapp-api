package ar.edu.unq.desapp.grupoB.backenddesappapi.services;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoB.backenddesappapi.repositories.ICryptocurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
public class CryptocurrencyService {

    @Autowired
    private ICryptocurrencyRepository cryptocurrencyRepository;

    @Transactional
    public Cryptocurrency save(Cryptocurrency crypto){
        return this.cryptocurrencyRepository.save(crypto);
    }

    @Transactional
    public Cryptocurrency findByID(Integer id) {
        return this.cryptocurrencyRepository.findById(id).get();
    }

    @Transactional
    public List<Cryptocurrency> findAll() {
        return this.cryptocurrencyRepository.findAll();
    }

    @Transactional
    public void deleteById(Integer id) {
        this.cryptocurrencyRepository.deleteById(id);
    }

    @Transactional
    public Cryptocurrency updateCrypto(Cryptocurrency crypto){ return this.cryptocurrencyRepository.save(crypto);}
}
