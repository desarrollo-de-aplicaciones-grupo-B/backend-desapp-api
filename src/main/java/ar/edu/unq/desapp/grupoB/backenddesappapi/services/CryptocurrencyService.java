package ar.edu.unq.desapp.grupoB.backenddesappapi.services;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.grupoB.backenddesappapi.repositories.ICryptocurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
public class CryptocurrencyService {

    @Autowired
    private ICryptocurrencyRepository cryptocurrencyRepository;

    @Transactional
    public List<Cryptocurrency> findAll(){
        return this.cryptocurrencyRepository.findAll();
    }

    @Transactional
    public List<Cryptocurrency> getAllCryptocurrencyPrices() {
        return this.cryptocurrencyRepository.findAll();
    }

    @Transactional
    public Cryptocurrency getCryptoByName(String crypto_nomenclature){
        return this.cryptocurrencyRepository.getCryptoByName(crypto_nomenclature);
    }

    @Transactional
    public void save(Cryptocurrency cryptocurrency){
        cryptocurrencyRepository.save(cryptocurrency);
    }

    @Transactional
    public void deleteById(Integer id){
        cryptocurrencyRepository.deleteById(id);
    }

    @Transactional
    public Optional<Cryptocurrency> findById(Integer id){ return this.cryptocurrencyRepository.findById(id); }
}
