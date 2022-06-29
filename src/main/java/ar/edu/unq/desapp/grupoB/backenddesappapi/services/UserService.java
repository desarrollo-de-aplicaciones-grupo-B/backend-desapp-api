package ar.edu.unq.desapp.grupoB.backenddesappapi.services;

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

    @Autowired
    private CryptocurrencyService cryptocurrencyService;

    @Autowired
    private CotizationService cotizationService;

    @Autowired
    private TradingAuditService tradingAuditService;

    @Autowired
    private TradingService tradingService;

    @Transactional
    public User save(User user){
        return this.userRepository.save(user);
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
            cotizationService.checkPriceMargin(cryptoId, cotization);
            tradingService.save(new Trading(cryptoId, cryptoAmount, cotization, operationAmount, userId));
        } catch (OutOfRangeCotizationException e){
            //TODO throw 500?
        } catch (Exception e) { // Not found
            //TODO throw 404
        }
    }

    @Transactional
    public void buy(Integer userId, Integer tradingId){
        Trading trading = tradingService.findByID(tradingId);
        trading.setBuyerId(userId);
    }

    @Transactional
    public void cancel(Integer cancellerId,Integer tradingId){
        Trading trading = tradingService.findByID(tradingId);
        User canceller = findByID(cancellerId);
        if(trading.getBuyerId() != null) { //If there is already a buyer then the canceller gets penalized
            if (trading.getBuyerId().equals(cancellerId)) {
                canceller.penalize();
            } else if (trading.getSellerId().equals(cancellerId)) {
                canceller.penalize();
            }
        }
        tradingService.deleteById(trading.getIdOperation());
    }

    @Transactional
    public void confirmTransfer(Integer userId,Integer tradingId){
        Trading trading = tradingService.findByID(tradingId);
        trading.confirmTransfer(userId);
        tradingService.updateTrading(trading);
    }

    @Transactional
    public TradingAudit confirmReception(Integer userId,Integer tradingId) {
        Trading trading = tradingService.findByID(tradingId);
        LocalDateTime confirmationDate = LocalDateTime.now();
        if (trading.getSellerId().equals(userId) && trading.isTransferConfirmed()) {
            try {
                checkPriceCotization(trading.getCryptoId(), trading.getCotization());
                Long timeDifference = ChronoUnit.MINUTES.between(trading.getCreationDate(), confirmationDate);
                User seller = findByID(userId);
                User buyer = findByID(trading.getBuyerId());
                seller.successfulTrading(timeDifference);
                buyer.successfulTrading(timeDifference);
                return createTransactionAudit(trading, seller, confirmationDate);
            } catch (OutOfRangeCotizationException e) {
                tradingService.deleteById(trading.getIdOperation());
                //TODO Tirar excepcion y avisar que se canceló la trading
            }

        } else if(!trading.getSellerId().equals(userId)){
            //TODO confirma recepcion un id que no es el vendedor, not authorized exception?
        }
        throw new Error(); //TODO transferencia no confirmada, tirar una excepcion o no?
    }

    @Transactional
    private TradingAudit createTransactionAudit(Trading trading, User seller, LocalDateTime confirmationDate) {
        TradingAudit tAudit = new TradingAudit();
        String cryptoName = cryptocurrencyService.findById(trading.getCryptoId()).get().getCryptoName();
        tAudit.setCryptocurrency(cryptoName);
        tAudit.setUser(seller.getName() + " " + seller.getLastname());
        tAudit.setCotization(trading.getCotization());
        tAudit.setCryptoAmount(trading.getCryptoAmount());
        tAudit.setOperationAmount(trading.getOperationAmount());
        tAudit.setHour(confirmationDate);
        tAudit.setShippingAddress(seller.getAddress());
        tAudit.setUserOperations(seller.getSuccessfulOperations());
        tAudit.setUserReputation(seller.getReputation());
        return tradingAuditService.save(tAudit);
    }

    public void checkPriceCotization(Integer cryptoId, Double cotization) throws OutOfRangeCotizationException {
         cotizationService.checkPriceMargin(cryptoId, cotization);
    }
}
