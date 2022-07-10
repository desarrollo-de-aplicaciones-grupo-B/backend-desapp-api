package ar.edu.unq.desapp.grupoB.backenddesappapi.services;

import ar.edu.unq.desapp.grupoB.backenddesappapi.model.DTO.CreateTransactionDTO;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.DTO.RegisterDTO;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Trading;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.TradingAudit;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Utils.DefinedError;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Utils.Exceptions.OutOfRangeCotizationException;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Utils.Exceptions.UserValidation;
import ar.edu.unq.desapp.grupoB.backenddesappapi.repositories.IUserRepository;
import org.omg.CORBA.UserException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service("UserService")
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void save(RegisterDTO user) {
        if(userExists(user.getEmail())){
            throw new UserValidation(DefinedError.ERROR_EMAIL_IS_IN_USE.getErrorCode(), DefinedError.ERROR_EMAIL_IS_IN_USE.getErrorMessage());
        }
        User userRegister = new User();
        userRegister.setName(user.getName());
        userRegister.setUserWallet(user.getUserWallet());
        userRegister.setAddress(user.getAddress());
        userRegister.setEmail(user.getEmail());
        userRegister.setLastname(user.getLastname());
        userRegister.setPassword(passwordEncoder.encode(user.getPassword()));
        userRegister.setCvu(user.getCvu());
        this.userRepository.save(userRegister);
    }

    @Transactional
    public User findByID(Integer id) {
        return this.userRepository.findById(id).get();
    }

    @Transactional
    public User findUserByName(String name) {
        return this.userRepository.findUserByName(name);
    }

    @Transactional
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Transactional
    public Boolean userExists (String email){
        return this.userRepository.existsUserByEmail(email);
    }

    @Transactional
    public void openTrading(Integer userId, CreateTransactionDTO createTransactionDTO) {
        try {
            cotizationService.checkPriceMargin(createTransactionDTO.getCryptoId(), createTransactionDTO.getCotization());
            tradingService.save(new Trading(createTransactionDTO.getCryptoId(), createTransactionDTO.getCryptoAmount(), createTransactionDTO.getCotization(), createTransactionDTO.getOperationAmount(), userId));
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
    public TradingAudit createTransactionAudit(Trading trading, User seller, LocalDateTime confirmationDate) {
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
