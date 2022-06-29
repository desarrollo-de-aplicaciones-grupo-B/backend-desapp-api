package ar.edu.unq.desapp.grupoB.backenddesappapi.webservices;

import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.grupoB.backenddesappapi.services.CryptocurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/crypto")
public class CryptocurrencyRestService {

    @Autowired
    private CryptocurrencyService cryptocurrencyService;

    @GetMapping(value = "/{crypto_nomenclature}")
    public Cryptocurrency getCryptoByName(@PathVariable("crypto_nomenclature") String crypto_nomenclature){
        return cryptocurrencyService.getCryptoByName(crypto_nomenclature);
    }

    @GetMapping()
    public List<Cryptocurrency> getAll(){
        return cryptocurrencyService.findAll();
    }

    @PostMapping
    public void createCrypto(@RequestBody Cryptocurrency cryptocurrency){
        cryptocurrencyService.saveCrypto(cryptocurrency);
    }

    @PutMapping
    public void update(@RequestBody Cryptocurrency cryptocurrency){
        cryptocurrencyService.saveCrypto(cryptocurrency);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteCrypto(@PathVariable("id") Integer id){
        cryptocurrencyService.deleteById(id);
    }
}
