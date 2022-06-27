package ar.edu.unq.desapp.grupoB.backenddesappapi.webservices;

import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Cotization;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.DTO.CryptoCotizationDTO;
import ar.edu.unq.desapp.grupoB.backenddesappapi.services.CotizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/cotization")
public class CotizationRestService {

    @Autowired
    private CotizationService cotizationService;
    @Autowired
    private RestTemplate restTemplate;


    @GetMapping
    public List<Object> getCryptoCotization(){

        String url = "https://api1.binance.com/api/v3/ticker/price?symbols=[\"ALICEUSDT\",\"MATICUSDT\",\"AXSUSDT\",\"AAVEUSDT\",\"ATOMUSDT\",\"NEOUSDT\",\"DOTUSDT\",\"ETHUSDT\",\"CAKEUSDT\",\"BTCUSDT\",\"BNBUSDT\",\"ADAUSDT\",\"TRXUSDT\",\"AUDIOUSDT\"]";
        Object[] objects = restTemplate.getForObject(url, Object[].class);

        return Arrays.asList(objects);
    }
}
