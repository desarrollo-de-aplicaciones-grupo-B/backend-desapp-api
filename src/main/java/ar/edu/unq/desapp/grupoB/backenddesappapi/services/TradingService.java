package ar.edu.unq.desapp.grupoB.backenddesappapi.services;

import ar.edu.unq.desapp.grupoB.backenddesappapi.model.DTO.TradingUserDTO;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Trading;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Utils.DefinedError;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Utils.Exceptions.UserValidation;
import ar.edu.unq.desapp.grupoB.backenddesappapi.repositories.ITradingRepository;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TradingService {

    @Autowired
    private ITradingRepository tradingRepository;


    @Transactional
    public Trading save(Trading trading){
        return this.tradingRepository.save(trading);
    }

    @Transactional
    public Trading findByID(Integer id) {
        try {
            return this.tradingRepository.findById(id).get();
        } catch (NoSuchElementException e){
            throw new UserValidation(DefinedError.NOT_FOUND.getErrorCode(), "Trading "+id+ DefinedError.NOT_FOUND.getErrorMessage());
        }
    }

    @Transactional
    public void deleteById(Integer id) {
        try {
            this.tradingRepository.deleteById(id);
        } catch (NoSuchElementException e){
            throw new UserValidation(DefinedError.NOT_FOUND.getErrorCode(), "Trading "+id+ DefinedError.NOT_FOUND.getErrorMessage());
        }
    }

    @Transactional
    public Trading updateTrading(Trading trading){ return (Trading) this.tradingRepository.save(trading);}

    @Transactional
    public List<TradingUserDTO> getAllTradingUserDTO(Integer userID){
        List<Object[]> array = tradingRepository.getAllUserTradings(userID);
        List<TradingUserDTO> dtos = new ArrayList<>();
        final java.sql.Timestamp[] creationDate = new java.sql.Timestamp[1];
        array.forEach(o -> {
            creationDate[0] = (Timestamp) o[0];
            dtos.add(new TradingUserDTO(
                    LocalDateTime.ofInstant(creationDate[0].toInstant(), ZoneId.of("America/Argentina/Buenos_Aires")),
                    (String) o[1],
                    (Double) o[2],
                    (Double) o[3],
                    (Double) o[4],
                    (String) o[5],
                    (Integer) o[6],
                    (Double) o[7]));
        });
        return dtos;
    }

}
