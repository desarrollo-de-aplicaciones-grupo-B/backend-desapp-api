package ar.edu.unq.desapp.grupoB.backenddesappapi.service;

import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.DTO.CreateTransactionDTO;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.DTO.RegisterDTO;

import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Trading;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.TradingAudit;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Utils.DefinedError;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Utils.Exceptions.UserValidation;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Utils.security.JwtRequest;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Utils.security.JwtTokenUtil;
import ar.edu.unq.desapp.grupoB.backenddesappapi.repositories.IUserRepository;
import ar.edu.unq.desapp.grupoB.backenddesappapi.services.*;

import org.junit.jupiter.api.*;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Autowired
    @InjectMocks
    private UserService uService;

    @Mock
    private IUserRepository uRepository;

    @Mock
    private TradingService tService;

    @Mock
    private CryptocurrencyService cryptocurrencyService;

    @Mock
    private CotizationService cotizationService;

    @Mock
    private TradingAuditService tradingAuditService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private JwtUserDetailsService userDetailsService;

    @Mock
    private JwtRequest anyJWTRequest;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private RegisterDTO anyUserDTO;

    @Mock
    private User anyUser;

    @Mock
    private User otherUser;

    @Mock
    private LocalDateTime anyLocalTime;

    //private RegisterDTO realUser =  new RegisterDTO("anyname", "anylastname", "any@email.com", "anyadress", "anyp4s5w","438327496","uh32y3");


    @Mock
    private CreateTransactionDTO anyTransactionDTO;

    @Mock
    private Trading anyTrading;

    @Mock
    private Cryptocurrency anyCrypto;

    @BeforeEach
    public void setUp(){
        uService = new UserService();

        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void createsANewUser(){
        when(uRepository.existsUserByEmail(anyUserDTO.getEmail())).thenReturn(false);
        when(uRepository.existsUserByName(anyUserDTO.getName())).thenReturn(false);
        when(anyUserDTO.getPassword()).thenReturn("anypassword");
        when(passwordEncoder.encode(anyUserDTO.getPassword())).thenReturn("encodedpassword");
        doNothing().when(anyUserDTO).setPassword("encodedpassword");

        uService.save(anyUserDTO);

        verify(uRepository).existsUserByEmail(anyUserDTO.getEmail());
        verify(uRepository).existsUserByName(anyUserDTO.getName());
        verify(anyUserDTO,times(3)).getPassword();
        verify(passwordEncoder).encode(anyUserDTO.getPassword());
        verify(uRepository).save(ArgumentMatchers.any());
    }

    @Test
    public void userAuthenticationSuccsessful(){
       assertDoesNotThrow(
               ()-> {uService.authenticate(anyJWTRequest);},
               "This method should not throw errors"
       );
       verify(authenticationManager).authenticate(any());
       verify(userDetailsService).loadUserByUsername(null);
       verify(jwtTokenUtil).generateToken(any());
    }
    @Test
    public void userAuthenticationThrowsInvalidCredentialsWhenCatchingAuthenticationError(){
        when(anyJWTRequest.getUsername()).thenReturn("anyusername");
        when(anyJWTRequest.getPassword()).thenReturn("anypassword");
        when(authenticationManager.authenticate(any())).thenThrow(mock(AuthenticationException.class));
        UserValidation thrown = assertThrows(UserValidation.class,
                                        ()-> {uService.authenticate(anyJWTRequest);},
                                        "This method should throw an INVALID_CREDENTIALS error"
                                );
        verify(authenticationManager).authenticate(any());
        assertEquals("Invalid Credentials", thrown.getMessage());
        assertEquals(401, thrown.getErrorCode());
    }

    @Test
    public void saveRepeatedEmailThrowsError(){
        when(uRepository.existsUserByEmail(anyUserDTO.getEmail())).thenReturn(true);

        UserValidation thrown = assertThrows(UserValidation.class,
                ()-> {uService.save(anyUserDTO);},
                "This method should throw error: The email is already in use, please choose another"
        );

        verify(uRepository).existsUserByEmail(anyUserDTO.getEmail());
        verify(uRepository, times(0)).existsUserByName(anyUserDTO.getName());
        verify(anyUserDTO,times(0)).getPassword();
        verifyNoInteractions(passwordEncoder);
        verify(uRepository,times(0)).save(ArgumentMatchers.any());

        assertEquals("The email is already in use, please choose another", thrown.getMessage());
        assertEquals(409, thrown.getErrorCode());
    }

    @Test
    public void saveRepeatedUserThrowsError(){
        when(uRepository.existsUserByName(anyUserDTO.getEmail())).thenReturn(false);
        when(uRepository.existsUserByName(anyUserDTO.getName())).thenReturn(true);

        UserValidation thrown = assertThrows(UserValidation.class,
                ()-> {uService.save(anyUserDTO);},
                "This method should throw error: The username is already in use, please choose another"
        );

        verify(uRepository).existsUserByEmail(anyUserDTO.getEmail());
        verify(uRepository).existsUserByName(anyUserDTO.getName());
        verify(anyUserDTO,times(0)).getPassword();
        verifyNoInteractions(passwordEncoder);
        verify(uRepository,times(0)).save(ArgumentMatchers.any());
        assertEquals("The username is already in use, please choose another", thrown.getMessage());
        assertEquals(409, thrown.getErrorCode());
    }

    @Test
    public void openTradingSuccessfuly(){
        when(anyUser.getId()).thenReturn(1);
        when(uRepository.findById(any())).thenReturn(Optional.of(anyUser));
        when(anyTransactionDTO.getCryptoId()).thenReturn(1);
        when(anyTransactionDTO.getCotization()).thenReturn(1.4);
        doNothing().when(cotizationService).checkPriceMargin(any(),any());

        uService.openTrading(anyUser.getId(),anyTransactionDTO);

        verify(uRepository).findById(any());
        verify(cotizationService).checkPriceMargin(any(),any());
        verify(anyTransactionDTO,times(2)).getCryptoId();
        verify(anyTransactionDTO,times(2)).getCotization();
        verify(tService,atLeastOnce()).save(any());
    }

    @Test
    public void openTradingThrowsUserNotFound(){
        when(anyUser.getId()).thenReturn(1);
        when(uRepository.findById(any())).thenThrow(new NoSuchElementException());

        UserValidation thrown = assertThrows(UserValidation.class,
                ()-> {uService.openTrading(anyUser.getId(),anyTransactionDTO);},
                "This method should throw error: User 1 not found"
        );

        verify(uRepository).findById(1);
        verifyNoInteractions(cotizationService);
        verifyNoInteractions(anyTransactionDTO);
        verifyNoInteractions(tService);
        assertEquals("User 1 not found", thrown.getMessage());
        assertEquals(404, thrown.getErrorCode());
    }
    @Test
    public void openTradingThrowsCotizationOutOfRangedException(){
        when(anyUser.getId()).thenReturn(1);
        when(anyTransactionDTO.getCryptoId()).thenReturn(1);
        when(anyTransactionDTO.getCotization()).thenReturn(25.5);
        when(uRepository.findById(any())).thenReturn(Optional.of(anyUser));
        doThrow(new UserValidation(DefinedError.OUT_OF_RANGE_COTIZATION.getErrorCode(), "The price is below system cotization by more than 5%"))
                .when(cotizationService).checkPriceMargin(any(),any());

        UserValidation thrown = assertThrows(UserValidation.class,  //Este assert me asegura que cuando cotizationService tire Out_of_range, userService lo va a tirar tambien
                ()-> {uService.openTrading(anyUser.getId(),anyTransactionDTO);},
                "This method should throw error: The price is below system cotization by more than 5%"
        );

        verify(anyTransactionDTO).getCotization();
        verify(anyTransactionDTO).getCryptoId();
        verify(uRepository).findById(1);
        verify(cotizationService).checkPriceMargin(1,25.5);
        verifyNoInteractions(tService);

        assertEquals("The price is below system cotization by more than 5%", thrown.getMessage());
        assertEquals(409, thrown.getErrorCode());
    }

    @Test
     void buySuccessful(){
        when(anyUser.getId()).thenReturn(1);
        when(anyTrading.getIdOperation()).thenReturn(1);
        when(uRepository.findById(anyInt())).thenReturn(Optional.of(anyUser));
        when(tService.findByID(any())).thenReturn(anyTrading);
        assertDoesNotThrow(
                ()-> {uService.buy(anyUser.getId(), anyTrading.getIdOperation());},
                "This method should not throw errors"
        );
        verify(uRepository).findById(anyInt());
        verify(tService).findByID(anyInt());
    }

    @Test
    void buyThrowsNotFoundException(){
        when(anyUser.getId()).thenReturn(1);
        when(anyTrading.getIdOperation()).thenReturn(1);
        when(uRepository.findById(anyInt())).thenReturn(Optional.of(anyUser));
        when(tService.findByID(any())).thenThrow(new UserValidation(DefinedError.NOT_FOUND.getErrorCode(), "any error message"));
        UserValidation thrown = assertThrows( UserValidation.class,
                ()-> {uService.buy(anyUser.getId(), anyTrading.getIdOperation());},
                "This method should throw Not found exception"
        );
        verify(uRepository).findById(anyInt());
        verify(tService).findByID(anyInt());
        assertEquals("any error message", thrown.getMessage());
        assertEquals(404, thrown.getErrorCode());
    }


    @Test
    void cancelSuccessful(){
        when(anyUser.getId()).thenReturn(1);
        when(anyTrading.getIdOperation()).thenReturn(1);
        when(uRepository.findById(anyInt())).thenReturn(Optional.of(anyUser));
        when(anyTrading.getSellerId()).thenReturn(1); //Simula que el vendedor es quien esta cancelando
        when(anyTrading.getBuyerId()).thenReturn(2);
        doNothing().when(tService).deleteById(1);


        when(tService.findByID(any())).thenReturn(anyTrading);
        assertDoesNotThrow(
                ()-> {uService.cancel(anyUser.getId(), anyTrading.getIdOperation());},
                "This method should not throw errors"
        );
        verify(uRepository).findById(anyInt());
        verify(tService).findByID(anyInt());
        verify(anyTrading).getSellerId();
        verify(anyTrading).getBuyerId();
        verify(tService).deleteById(anyInt());
    }
    @Test
    void cancelThrowsNotFound(){
        when(anyUser.getId()).thenReturn(1);
        when(anyTrading.getIdOperation()).thenReturn(1);
        when(uRepository.findById(anyInt())).thenThrow(mock(NoSuchElementException.class));

        when(tService.findByID(any())).thenReturn(anyTrading);
        UserValidation thrown = assertThrows(UserValidation.class,
                ()-> {uService.cancel(anyUser.getId(), anyTrading.getIdOperation());},
                "This method should throw not found exception"
        );
        verify(uRepository).findById(anyInt());
        verifyNoInteractions(tService);

        assertEquals("User 1 not found", thrown.getMessage());
        assertEquals(404, thrown.getErrorCode());
    }

    @Test
    void unauthorizedUserTriesToCancel(){
        when(anyTrading.getIdOperation()).thenReturn(1);
        when(uRepository.findById(anyInt())).thenReturn(Optional.of(anyUser));
        when(tService.findByID(anyInt())).thenReturn(anyTrading);
        when(anyTrading.getSellerId()).thenReturn(1);
        when(anyTrading.getBuyerId()).thenReturn(2);

        UserValidation thrown = assertThrows(UserValidation.class,
                ()-> {uService.cancel(99, anyTrading.getIdOperation());},
                "This method should throw Forbidden exception"
        );

        verify(uRepository).findById(anyInt());
        verify(tService).findByID(anyInt());
        verify(anyTrading).getSellerId();
        verify(anyTrading).getBuyerId();

        assertEquals("User 99 not authorized to cancel the trading", thrown.getMessage());
        assertEquals(403, thrown.getErrorCode());
    }

    @Test
    void userGetsPenalizedWhenCancelling(){
        when(anyTrading.getIdOperation()).thenReturn(1);
        when(uRepository.findById(anyInt())).thenReturn(Optional.of(anyUser));
        when(tService.findByID(anyInt())).thenReturn(anyTrading);
        when(anyTrading.getSellerId()).thenReturn(1);
        when(anyTrading.getBuyerId()).thenReturn(2);

        assertDoesNotThrow(
                ()-> {uService.cancel(2, anyTrading.getIdOperation());},
                "This method should not throw errors"
        );

        verify(uRepository).findById(anyInt());
        verify(tService).findByID(anyInt());
        verify(anyTrading).getSellerId();
        verify(anyTrading,times(2)).getBuyerId(); //pregunta dos veces: if !seller == canceller && !buyer == canceller
        verify(anyUser).penalize();//                                                                     true        &&        false     => da false, va al else if  buyer != null
        verify(tService).deleteById(anyInt());
    }

    @Test
    void confirmReceptionSuccessful(){
        //confirm reception
        when(anyUser.getId()).thenReturn(1);
        when(anyTrading.getIdOperation()).thenReturn(1);
        when(uRepository.findById(1)).thenReturn(Optional.of(anyUser));
        when(tService.findByID(anyInt())).thenReturn(anyTrading);
        when(anyTrading.getSellerId()).thenReturn(1);
        when(anyTrading.getBuyerId()).thenReturn(2);
        when(anyTrading.isTransferConfirmed()).thenReturn(true);
        doNothing().when(cotizationService).checkPriceMargin(anyInt(),anyDouble());
        when(anyTrading.getCreationDate()).thenReturn(anyLocalTime);
        when(uRepository.findById(2)).thenReturn(Optional.of(otherUser));

        //Create transaction audit
        when(cryptocurrencyService.findById(anyInt())).thenReturn(Optional.of(anyCrypto));
        when(anyCrypto.getCryptoName()).thenReturn("anycrypto");

        assertDoesNotThrow(
                ()-> {uService.confirmReception(anyUser.getId(), anyTrading.getIdOperation());},
                "This method should not throw errors"
        );
        verify(uRepository, times(3)).findById(anyInt());
        verify(tService).findByID(anyInt());
        verify(anyTrading).getSellerId();
        verify(anyTrading).getBuyerId();
        verify(anyTrading).isTransferConfirmed();
        verify(anyTrading).getCreationDate();
        verify(anyUser).successfulTrading(anyLong());
        verify(otherUser).successfulTrading(anyLong());
        verify(tService).deleteById(anyInt());
        verify(cotizationService).checkPriceMargin(anyInt(),anyDouble());
        verify(cryptocurrencyService).findById(anyInt());
        verify(anyCrypto).getCryptoName();
        verify(tradingAuditService).save(any(TradingAudit.class));
    }
}
