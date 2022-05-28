package ar.edu.unq.desapp.grupoB.backenddesappapi.webservices;


import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Trading;
import ar.edu.unq.desapp.grupoB.backenddesappapi.services.TradingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trading")
public class TradingRestService {

    @Autowired
    private TradingService tradingService;

    @GetMapping
    public List<Trading> getAll(){
        return tradingService.findAll();
    }

    @PostMapping
    public void save(@RequestBody Trading trading){
        tradingService.save(trading);
    }

    @GetMapping(value = "/{id}")
    public Trading getById(@PathVariable("id") Integer id){
        return tradingService.findByID(id);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteById(@PathVariable("id") Integer id){
        tradingService.deleteById(id);
    }

    @PostMapping(value = "/{id}")
    public void update(@RequestBody Trading trading) { tradingService.updateTrading(trading);
    }
}
