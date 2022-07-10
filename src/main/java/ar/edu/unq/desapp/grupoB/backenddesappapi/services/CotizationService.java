package ar.edu.unq.desapp.grupoB.backenddesappapi.services;

import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Cotization;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Utils.Exceptions.OutOfRangeCotizationException;
import ar.edu.unq.desapp.grupoB.backenddesappapi.repositories.ICotizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CotizationService {

    @Autowired
    private ICotizationRepository cotizationRepository;

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
    public List<Cotization> findAll(){
        return this.cotizationRepository.findAll();
    }

    @Transactional
    public List<Cotization> findLast24HoursCotizations(String name){
        return this.cotizationRepository.findLast24HoursCotizations(name);
    }
    public void checkPriceMargin(Integer cryptoId, Double cotization) throws OutOfRangeCotizationException {
        Optional<Cotization> systemCotization = findById(cryptoId);
        if(systemCotization.isPresent()){
            Double margin = 5 * systemCotization.get().getPriceCotization() / 100;
            Double min = systemCotization.get().getPriceCotization() - margin;
            Double max = systemCotization.get().getPriceCotization() + margin;
            if(cotization < min){
                throw new OutOfRangeCotizationException("The price is below system cotization by more than 5%");
            }
            if(cotization > max){
                throw new OutOfRangeCotizationException("The price is above system cotization by more than 5%");
            }
        } //TODO throw error not found
    }

    @Transactional
    public Cotization findLastCotization(String cryptoNomenclature){
        return cotizationRepository.findLastCotization(cryptoNomenclature);

    }
}
