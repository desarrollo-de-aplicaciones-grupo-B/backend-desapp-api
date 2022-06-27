package ar.edu.unq.desapp.grupoB.backenddesappapi.services;

import ar.edu.unq.desapp.grupoB.backenddesappapi.model.DTO.RegisterDTO;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Trading;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.TradingAudit;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Utils.Exceptions.OutOfRangeCotizationException;
import ar.edu.unq.desapp.grupoB.backenddesappapi.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private IUserRepository userRepository;

    @Transactional
    public User save(User user) {
          return userRepository.save(user);
    }

    @Transactional
    public User findByID(Integer id) {
        return this.userRepository.findById(id).get();
    }

    @Transactional
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Transactional
    public void deleteById(Integer id) {
        this.userRepository.deleteById(id);
    }

    @Transactional
    public void updateUser(User userUpdate, Integer userId){
        Optional<User> userFound = userRepository.findById(userId);

        User user = userFound.get();
        user.setName(userUpdate.getName());
        user.setLastname(userUpdate.getLastname());
        user.setEmail(userUpdate.getEmail());
        user.setAddress(userUpdate.getAddress());
        user.setCvu(userUpdate.getCvu());
        user.setUserWallet(userUpdate.getUserWallet());
    }

    @Transactional
    public User findUserByName(String username) { return this.userRepository.findUserByName(username);}

    @Transactional
    public void openTrading(Integer userId, Integer cryptoId, Double cryptoAmount, Double cotization, Double operationAmount) {
        try {
            cotizationService().checkPriceMargin(cryptoId, cotization);
            tradingService().save(new Trading(cryptoId, cryptoAmount, cotization, operationAmount, userId));
        } catch (OutOfRangeCotizationException e){
            //TODO throw 500?
        } catch (Exception e) { // Not found
            //TODO throw 404
        }
    }

    @Transactional
    public void buy(Integer userId, Integer tradingId){
        Trading trading = tradingService().findByID(tradingId);
        trading.setBuyerId(userId);
    }

    @Transactional
    public void cancel(Integer userId,Integer tradingId){
        Trading trading = tradingService().findByID(tradingId);
        User canceller = findByID(userId);
        if(trading.getBuyerId().equals(userId)){
            canceller.penalize();
        } else
        if(trading.getSellerId().equals(userId) && trading.getBuyerId() != null){ //If there is not a buyer yet the seller wont get penalized
            canceller.penalize();
        }
        tradingService().deleteById(trading.getIdOperation());
    }

    @Transactional
    public void confirmTransfer(Integer userId,Integer tradingId){
        Trading trading = tradingService().findByID(tradingId);
        trading.confirmTransfer(userId);
        tradingService().updateTrading(trading);
    }

    @Transactional
    public void confirmReception(Integer userId,Integer tradingId){
        Trading trading = tradingService().findByID(tradingId);
        LocalDateTime confirmationDate = LocalDateTime.now();
        if(trading.getSellerId().equals(userId)) {
            if(cotizationIsOK(trading.getCryptoId(), trading.getCotization())){
                Long timeDifference = ChronoUnit.MINUTES.between(trading.getCreationDate(), confirmationDate);
                User seller = findByID(userId);
                User buyer = findByID(trading.getBuyerId());
                seller.successfulTrading(timeDifference);
                buyer.successfulTrading(timeDifference);

                TradingAudit tAudit = new TradingAudit();
                tAudit.setUser(seller.getName() + " " + seller.getLastname());
                tAudit.setCotization(trading.getCotization());
                tAudit.setCryptoAmount(trading.getCryptoAmount());
                tAudit.setOperationAmount(trading.getOperationAmount());
                tAudit.setHour(confirmationDate);
                tAudit.setShippingAddress(seller.getAddress());
                tradingAuditService().save(tAudit);
            } else {
                tradingService().deleteById(trading.getIdOperation());
            }
        }
    }
    public boolean cotizationIsOK(Integer cryptoId, Double cotization){
        return cotizationService().cotizationIsOK(cryptoId, cotization);
    }
    public TradingService tradingService(){
        return new TradingService();
    }
    public CotizationService cotizationService(){
        return new CotizationService();
    }
    public TradingAuditService tradingAuditService() { return new TradingAuditService(); }


}
