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

    @GetMapping
    public List<Cryptocurrency> getAll(){
        return cryptocurrencyService.findAll();
    }

    @PostMapping
    public void saveUser(@RequestBody Cryptocurrency crypto){
        cryptocurrencyService.save(crypto);
    }

    @GetMapping(value = "/{id}")
    public Cryptocurrency getById(@PathVariable("id") Integer id){
        return cryptocurrencyService.findByID(id);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteById(@PathVariable("id") Integer id){
        cryptocurrencyService.deleteById(id);
    }

    @PutMapping()
    public void updateUser(@RequestBody Cryptocurrency crypto){
        cryptocurrencyService.updateCrypto(crypto);
    }
}
