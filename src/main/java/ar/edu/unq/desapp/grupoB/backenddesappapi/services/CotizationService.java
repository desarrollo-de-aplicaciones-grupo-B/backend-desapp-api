package ar.edu.unq.desapp.grupoB.backenddesappapi.services;

import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Cotization;
import ar.edu.unq.desapp.grupoB.backenddesappapi.repositories.ICotizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CotizationService {

    @Autowired
    private ICotizationRepository cotizationRepository;

    @Transactional
    public Cotization save(Cotization cotization){
        return this.cotizationRepository.save(cotization);
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
    public List<Cotization> findLast24HoursCotizations(Integer id){
        return this.cotizationRepository.findLast24HoursCotizations(id);
    }

    public boolean cotizationIsOK(Integer cryptoId, Double tradingCotization) {
        Optional<Cotization> systemCotization = findById(cryptoId);
        return systemCotization.filter(cotization -> (tradingCotization.equals(cotization.getPriceCotization()))).isPresent();
    }
}