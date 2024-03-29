package ar.edu.unq.desapp.grupoB.backenddesappapi.services;

import ar.edu.unq.desapp.grupoB.backenddesappapi.model.DTO.CreateTransactionDTO;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.DTO.RegisterDTO;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Trading;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.TradingAudit;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Utils.DefinedError;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Utils.Exceptions.UserValidation;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Utils.security.JwtRequest;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Utils.security.JwtResponse;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Utils.security.JwtTokenUtil;
import ar.edu.unq.desapp.grupoB.backenddesappapi.repositories.IUserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

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

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Transactional
    public User save(RegisterDTO user) {

        if(emailIsInUse(user.getEmail())){
            throw new UserValidation(DefinedError.ERROR_EMAIL_IS_IN_USE.getErrorCode(), DefinedError.ERROR_EMAIL_IS_IN_USE.getErrorMessage());
        }

        if(nameIsInUse(user.getName())){
            throw new UserValidation(DefinedError.ERROR_NAME_IS_IN_USE.getErrorCode(), DefinedError.ERROR_NAME_IS_IN_USE.getErrorMessage());
        }

        User userRegister = new User();
        BeanUtils.copyProperties(user, userRegister);
        userRegister.setPassword(this.passwordEncoder.encode(user.getPassword()));
        return (this.userRepository.save(userRegister));

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
    public Boolean nameIsInUse (String name){
        return this.userRepository.existsUserByName(name);
    }

    @Transactional
    public Boolean emailIsInUse (String email){
        return this.userRepository.existsUserByEmail(email);
    }

    @Transactional
    public Trading openTrading(Integer userId, CreateTransactionDTO createTransactionDTO) {
       try{
            this.findByID(userId);
            cotizationService.checkPriceMargin(createTransactionDTO.getCryptoId(), createTransactionDTO.getCotization());
            return tradingService.save(new Trading(createTransactionDTO.getCryptoId(), createTransactionDTO.getCryptoAmount(), createTransactionDTO.getCotization(), createTransactionDTO.getOperationAmount(), userId));
       } catch(NoSuchElementException e){
           throw new UserValidation(DefinedError.NOT_FOUND.getErrorCode(),"User "+userId + DefinedError.NOT_FOUND.getErrorMessage());
       }
    }

    public JwtResponse authenticate(JwtRequest authenticationRequest) {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));

            UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

            String token = jwtTokenUtil.generateToken(userDetails);

            return new JwtResponse(token);

        } catch (AuthenticationException e) {
            throw new UserValidation(DefinedError.INVALID_CREDENTIALS.getErrorCode(), DefinedError.INVALID_CREDENTIALS.getErrorMessage());
        }
    }

    @Transactional
    public void buy(Integer userId, Integer tradingId){
        try{
            this.findByID(userId);
            Trading trading = tradingService.findByID(tradingId);
            trading.setBuyerId(userId);
        } catch (NoSuchElementException e){
            throw new UserValidation(DefinedError.NOT_FOUND.getErrorCode(), "User "+userId+DefinedError.NOT_FOUND.getErrorMessage());
        }
    }

    @Transactional
    public Trading cancel(Integer cancellerId,Integer tradingId) {
        try {
            User canceller = findByID(cancellerId);
            Trading trading = tradingService.findByID(tradingId);
            if (!Objects.equals(trading.getSellerId(), cancellerId) && !Objects.equals(trading.getBuyerId(), cancellerId)) { //User not authorized to cancel the trading
                throw new UserValidation(DefinedError.FORBIDDEN_ACTION.getErrorCode(), "User " + cancellerId + " not authorized to cancel the trading");
            } else if (trading.getBuyerId() != null) {  //If there is already a buyer then the canceller gets penalized
                canceller.penalize();
            }
            tradingService.deleteById(trading.getIdOperation());
            return trading;
        } catch (NoSuchElementException e) {
            throw new UserValidation(DefinedError.NOT_FOUND.getErrorCode(), "User " + cancellerId + DefinedError.NOT_FOUND.getErrorMessage());
        }
    }

    @Transactional
    public void confirmTransfer(Integer userId,Integer tradingId){
        try {
            this.findByID(userId);
            Trading trading = tradingService.findByID(tradingId);
            trading.confirmTransfer(userId);
            tradingService.updateTrading(trading);
        } catch(NoSuchElementException e){
            throw new UserValidation(DefinedError.NOT_FOUND.getErrorCode(), "User "+userId+DefinedError.NOT_FOUND.getErrorMessage());
        }
    }

    @Transactional
    public TradingAudit confirmReception(Integer userId,Integer tradingId) {
        try {
            this.findByID(userId);
        }
        catch (NoSuchElementException e){
            throw new UserValidation(DefinedError.NOT_FOUND.getErrorCode(), "User "+userId+DefinedError.NOT_FOUND.getErrorMessage());
        }
        Trading trading = null;
        try {
            trading = tradingService.findByID(tradingId);  //can throw not found
            LocalDateTime confirmationDate = LocalDateTime.now();
            if (trading.getSellerId().equals(userId) && trading.isTransferConfirmed()) {
                this.checkPriceCotization(trading.getCryptoId(), trading.getCotization());  //can throw user validation(out of range)
                Long timeDifference = ChronoUnit.MINUTES.between(trading.getCreationDate(), confirmationDate);
                User seller = findByID(userId);
                User buyer = findByID(trading.getBuyerId());
                seller.successfulTrading(timeDifference);
                buyer.successfulTrading(timeDifference);
                tradingService.deleteById(trading.getIdOperation());
                return createTransactionAudit(trading, seller, confirmationDate);
            } else if (!trading.getSellerId().equals(userId)) {
                throw new UserValidation(DefinedError.FORBIDDEN_ACTION.getErrorCode(), "User " + userId + " not authorized to confirm reception");
            } else {
                throw new UserValidation(DefinedError.FORBIDDEN_ACTION.getErrorCode(), "Transfer not confirmed yet");
            }
        } catch (UserValidation e) {
            tradingService.deleteById(trading.getIdOperation());
            throw new UserValidation(DefinedError.OUT_OF_RANGE_COTIZATION.getErrorCode(),DefinedError.OUT_OF_RANGE_COTIZATION.getErrorMessage());
        }
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

    public void checkPriceCotization(Integer cryptoId, Double cotization) throws UserValidation {
         cotizationService.checkPriceMargin(cryptoId, cotization);
    }
}
