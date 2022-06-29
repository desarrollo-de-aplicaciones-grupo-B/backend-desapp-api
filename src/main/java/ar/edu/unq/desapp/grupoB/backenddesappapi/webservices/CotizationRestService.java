package ar.edu.unq.desapp.grupoB.backenddesappapi.webservices;

import ar.edu.unq.desapp.grupoB.backenddesappapi.model.BinanceCotization;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Cotization;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.DTO.CryptoCotizationDTO;
import ar.edu.unq.desapp.grupoB.backenddesappapi.services.CotizationService;
import org.ietf.jgss.ChannelBinding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.websocket.server.PathParam;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

@RestController
@RequestMapping("/cotization")
public class CotizationRestService {

    @Autowired
    private CotizationService cotizationService;
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping
    public List<Cotization> getAll(){
        return cotizationService.findAll();
    }

    @GetMapping(value = "/{name}")
    public List<Cotization> getLast24oursCotization(@PathVariable("name") String name){
        return cotizationService.findLast24HoursCotizations(name);
    }

    @PostMapping(value = "/binance")
    public void saveCryptoCotizationBinance() throws ParseException {

        String url = "https://api1.binance.com/api/v3/ticker/price?symbols=[\"ALICEUSDT\",\"MATICUSDT\",\"AXSUSDT\",\"AAVEUSDT\",\"ATOMUSDT\",\"NEOUSDT\",\"DOTUSDT\",\"ETHUSDT\",\"CAKEUSDT\",\"BTCUSDT\",\"BNBUSDT\",\"ADAUSDT\",\"TRXUSDT\",\"AUDIOUSDT\"]";
        Object[] objects = restTemplate.getForObject(url, Object[].class);

        List<Cotization> cotizationList = new ArrayList<Cotization>();
        List<Object> rta = Arrays.asList(objects);

        LocalDateTime date = LocalDateTime.now();
        for (int i=0; i<rta.size(); i++) {
            String symbol = (String) ((LinkedHashMap) rta.get(i)).get("symbol");
            String price = (String) ((LinkedHashMap) rta.get(i)).get("price");
            Cotization maincra = new Cotization(date, Double.parseDouble(price), symbol);
            cotizationList.add(maincra);
        }

        for (Cotization coti: cotizationList) {
            cotizationService.save(coti);
        }
    }

    @PostMapping
    public void saveCotization(@RequestBody Cotization cotization){
         cotizationService.save(cotization);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteCotization(@PathVariable("id") Integer id){
        cotizationService.deleteById(id);
    }
}
