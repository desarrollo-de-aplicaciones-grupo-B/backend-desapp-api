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
    public List<Cryptocurrency> getAllCryptocurrencyPrices() {
        return this.cryptocurrencyRepository.findAll();
    }

}
