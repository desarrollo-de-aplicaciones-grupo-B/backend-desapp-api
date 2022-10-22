package ar.edu.unq.desapp.grupoB.backenddesappapi.service;

import ar.edu.unq.desapp.grupoB.backenddesappapi.model.DTO.CreateTransactionDTO;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.DTO.RegisterDTO;

import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Trading;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Utils.Exceptions.UserValidation;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Utils.security.JwtTokenUtil;
import ar.edu.unq.desapp.grupoB.backenddesappapi.repositories.IUserRepository;
import ar.edu.unq.desapp.grupoB.backenddesappapi.services.*;

import org.junit.jupiter.api.*;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    private AuthenticationManager authenticationManager;

    @Mock
    private RegisterDTO anyUserDTO;

    @Mock
    private User anyUser;

    //private RegisterDTO realUser =  new RegisterDTO("anyname", "anylastname", "any@email.com", "anyadress", "anyp4s5w","438327496","uh32y3");


    @Mock
    private CreateTransactionDTO anyTransactionDTO;

    @Mock
    private Trading anyTrading;

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
    public void saveRepeatedEmailThrowsError(){
        when(uRepository.existsUserByEmail(anyUserDTO.getEmail())).thenReturn(true);

        assertThrows(UserValidation.class,
                ()-> {uService.save(anyUserDTO);},
                "This method should throw error: The email is already in use, please choose another"
        );
        verify(uRepository).existsUserByEmail(anyUserDTO.getEmail());
        verify(uRepository, times(0)).existsUserByName(anyUserDTO.getName());
        verify(anyUserDTO,times(0)).getPassword();
        verifyNoInteractions(passwordEncoder);
        verify(uRepository,times(0)).save(ArgumentMatchers.any());
    }

    @Test
    public void saveRepeatedUserThrowsError(){
        when(uRepository.existsUserByName(anyUserDTO.getEmail())).thenReturn(false);
        when(uRepository.existsUserByName(anyUserDTO.getName())).thenReturn(true);

        assertThrows(UserValidation.class,
                ()-> {uService.save(anyUserDTO);},
                "This method should throw error: The username is already in use, please choose another"
        );
        verify(uRepository).existsUserByEmail(anyUserDTO.getEmail());
        verify(uRepository).existsUserByName(anyUserDTO.getName());
        verify(anyUserDTO,times(0)).getPassword();
        verifyNoInteractions(passwordEncoder);
        verify(uRepository,times(0)).save(ArgumentMatchers.any());
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
    public void test3(){

    }

}
