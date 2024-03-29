package ar.edu.unq.desapp.grupoB.backenddesappapi.services;

import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Cotization;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Utils.DefinedError;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Utils.Exceptions.UserValidation;
import ar.edu.unq.desapp.grupoB.backenddesappapi.repositories.ICotizationRepository;
import ar.edu.unq.desapp.grupoB.backenddesappapi.repositories.ICryptocurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.cache.annotation.Cacheable;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CotizationService {

    @Autowired
    private ICotizationRepository cotizationRepository;

    @Autowired
    private ICryptocurrencyRepository cryptoRepository;

    @Transactional
    public void save(Cotization cotization){
         this.cotizationRepository.save(cotization);
    }

    @Transactional
    public void deleteById(Integer id) {
        this.cotizationRepository.deleteById(id);
    }

    @Transactional
    public Cotization update(Cotization cotization){ return this.cotizationRepository.save(cotization);}

    @Transactional
    public Optional<Cotization> findById(Integer id){
        return this.cotizationRepository.findById(id);
    }

    @Transactional
    @Cacheable(cacheNames = "cryptoCotization")
    public List<Cotization> findAll(){
        addDelay();
        return this.cotizationRepository.findAll();
    }

    @Transactional
    public List<Cotization> findLast24HoursCotizations(String name){
        return this.cotizationRepository.findLast24HoursCotizations(name);
    }
    public void checkPriceMargin(Integer cryptoId, Double cotization) {
        Optional<Cryptocurrency> crypto = cryptoRepository.findById(cryptoId);
        Optional<Cotization> systemCotization = Optional.ofNullable(findLastCotization(crypto.get().getNomenclature()));
        Double margin = 5 * systemCotization.get().getPriceCotization() / 100;
        Double min = systemCotization.get().getPriceCotization() - margin;
        Double max = systemCotization.get().getPriceCotization() + margin;
        if(cotization < min){
            throw new UserValidation(DefinedError.OUT_OF_RANGE_COTIZATION.getErrorCode(),"The price is below system cotization by more than 5%");
        }
        if(cotization > max){
            throw new UserValidation(DefinedError.OUT_OF_RANGE_COTIZATION.getErrorCode(),"The price is above system cotization by more than 5%");
        }
    }

    @Transactional
    public Cotization findLastCotization(String cryptoNomenclature){
        return cotizationRepository.findLastCotization(cryptoNomenclature);

    }


    public void addDelay(){
        try{
            long time = 5000L;
            Thread.sleep(time);
        }
        catch(Exception e){
            throw new IllegalStateException(e);
        }
    }
}
