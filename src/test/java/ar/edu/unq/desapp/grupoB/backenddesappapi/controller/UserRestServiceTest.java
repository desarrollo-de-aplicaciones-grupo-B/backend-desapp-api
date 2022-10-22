package ar.edu.unq.desapp.grupoB.backenddesappapi.controller;

import ar.edu.unq.desapp.grupoB.backenddesappapi.model.DTO.CreateTransactionDTO;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.DTO.RegisterDTO;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Utils.DefinedError;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Utils.Exceptions.UserValidation;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Utils.security.JwtRequest;
import ar.edu.unq.desapp.grupoB.backenddesappapi.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;


import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = ar.edu.unq.desapp.grupoB.backenddesappapi.BackendDesappApiApplication.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserRestServiceTest {

    @Autowired
    private MockMvc mockMvc;

    private RegisterDTO anyUser() {
        return new RegisterDTO("anyname", "anylastname", "any@email.com", "anyadress", "anyp4s5w","438327496","uh32y3");
    }

    private CreateTransactionDTO anyTransaction(){
        return new CreateTransactionDTO(1,54.87,23.6, 543.5);
    };

    @MockBean
    private UserService userService;

    @BeforeEach
    public void setUp() throws Exception {
        mockMvc.perform(post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(anyUser().toString())
                .accept(MediaType.APPLICATION_JSON));

    }

    @Test
    public void registerOk() throws Exception {
        RegisterDTO registerDTO = new RegisterDTO("TobiTe1","Torres","tobi@gmail.com","casalinda","1234","123456789","test");

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registerDTO.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        verify(userService).save(ArgumentMatchers.refEq(registerDTO));
    }

    @Test
    public void registerBadRequest() throws Exception {
        RegisterDTO badDTO = anyUser();
        badDTO.setName("");

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(badDTO.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\n 'errors': [\n\t\t'The name must not be empty'\n\t]}"));
    }
    @Test
    public void emailAlreadyTakenTest() throws Exception{
        when(userService.save(ArgumentMatchers.refEq(anyUser()))).thenCallRealMethod();
        when(userService.emailIsInUse("any@email.com")).thenReturn(true);

        RegisterDTO existingEmailUser =anyUser();
        existingEmailUser.setEmail("any@email.com");
        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(existingEmailUser.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(content().json("{'errorMessage': 'The email is already in use, please choose another'}"));
        verify(userService,atLeastOnce()).save(ArgumentMatchers.refEq(anyUser()));
        verify(userService).emailIsInUse("any@email.com");
    }
    @Test
    public void nameAlreadyTakenTest() throws Exception{
        when(userService.save(ArgumentMatchers.refEq(anyUser()))).thenCallRealMethod();
        when(userService.emailIsInUse(anyUser().getEmail())).thenReturn(false);
        when(userService.nameIsInUse("anyname")).thenReturn(true);

        RegisterDTO existingNameUser = anyUser();
        existingNameUser.setName("anyname");
        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(existingNameUser.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(content().json("{'errorMessage': 'The username is already in use, please choose another'}"));
        verify(userService,atLeastOnce()).save(ArgumentMatchers.refEq(anyUser()));
        verify(userService, atLeastOnce()).nameIsInUse("anyname");
    }
    @Test
    public void loginOk() throws Exception {
        JwtRequest expectedUsernamePassword = new JwtRequest("anyname","anyp4s5w");

        mockMvc.perform(post("/users/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(expectedUsernamePassword.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userService).authenticate(ArgumentMatchers.refEq((expectedUsernamePassword)));
    }

    @Test
    public void authenticationFailed() throws Exception {
        JwtRequest wrongUserOrPassword = new JwtRequest("anyname","wrongpassword");
        when(userService.authenticate(ArgumentMatchers.refEq(wrongUserOrPassword))).thenThrow(new UserValidation(DefinedError.INVALID_CREDENTIALS.getErrorCode(), DefinedError.INVALID_CREDENTIALS.getErrorMessage()));

        mockMvc.perform(post("/users/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(wrongUserOrPassword.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(content().json("{'errorMessage': 'Invalid Credentials'}"));

        verify(userService).authenticate(ArgumentMatchers.refEq((wrongUserOrPassword)));
    }
    @Test
    public void getAllUser() throws Exception{

        mockMvc.perform(get("/users/allUser")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userService).findAll();
    }

    @Test
    public void openTradingOk() throws Exception {

        mockMvc.perform(post("/users/1/newTrading")
                .contentType(MediaType.APPLICATION_JSON)
                .content(anyTransaction().toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        verify(userService).openTrading(eq(1), ArgumentMatchers.refEq((anyTransaction())));
    }

    @Test
    public void openTradingThrowsOutOfRangeCotizationError() throws Exception {
        when(userService.openTrading(eq(1),ArgumentMatchers.refEq(anyTransaction())))
                .thenThrow(new UserValidation(DefinedError.OUT_OF_RANGE_COTIZATION.getErrorCode(), "The price is below system cotization by more than 5%"));
        mockMvc.perform(post("/users/1/newTrading")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(anyTransaction().toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(content().json("{'errorMessage': 'The price is below system cotization by more than 5%'}"));
        verify(userService).openTrading(eq(1), ArgumentMatchers.refEq((anyTransaction())));
    }

    @Test
    public void nonExistentUserOpenTrading() throws Exception {
        when(userService.openTrading(eq(99),ArgumentMatchers.refEq(anyTransaction()))).thenCallRealMethod();
        when(userService.findByID(99)).thenReturn(null);
        mockMvc.perform(post("/users/99/newTrading")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(anyTransaction().toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{'errorMessage': 'User 99 not found'}"));
        verify(userService).openTrading(eq(99), ArgumentMatchers.refEq((anyTransaction())));
    }

    @Test
    public void buyTradingOk() throws Exception {
        Mockito.doNothing().when(userService).buy(14,27);
        mockMvc.perform(put("/users/14/buy/27")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(userService).buy(14,27);
    }
    @Test
    public void nonExistentUserBuyTrading() throws Exception {
        doCallRealMethod().when(userService).buy(99,27);
        when(userService.findByID(99)).thenThrow(new UserValidation(DefinedError.NOT_FOUND.getErrorCode(),"User 99"+DefinedError.NOT_FOUND.getErrorMessage()));
        mockMvc.perform(put("/users/99/buy/27")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{'errorMessage': 'User 99 not found'}"));
        verify(userService).buy(99,27);
    }
   @Test
   public void confirmTransferOk() throws Exception {
      mockMvc.perform(put("/users/14/confirmTransfer/27")
              .contentType(MediaType.APPLICATION_JSON)
              .accept(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk());
       verify(userService).confirmTransfer(14,27);
   }
   @Test
    public void nonAuthorizedUserConfirmsTransfer() throws Exception {
        doThrow(new UserValidation(DefinedError.FORBIDDEN_ACTION.getErrorCode(), "User 14 not authorized to confirm transfer"))
                .when(userService).confirmTransfer(14,27);
        mockMvc.perform(put("/users/14/confirmTransfer/27")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(content().json("{'errorMessage': 'User 14 not authorized to confirm transfer'}"));
       verify(userService).confirmTransfer(14,27);
    }


   @Test
   public void confirmReceptionOk() throws Exception {
       mockMvc.perform(put("/users/13/confirmReception/26")
               .contentType(MediaType.APPLICATION_JSON)
               .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
       verify(userService).confirmReception(13,26);
   }
   @Test
   public void nonAuthorizedUserConfirmReception() throws Exception {
       doThrow(new UserValidation(DefinedError.FORBIDDEN_ACTION.getErrorCode(),"User 13 not authorized to confirm reception"))
               .when(userService).confirmReception(13,26);
       mockMvc.perform(put("/users/13/confirmReception/26")
                       .contentType(MediaType.APPLICATION_JSON)
                       .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isForbidden())
               .andExpect(content().json("{'errorMessage': 'User 13 not authorized to confirm reception'}"));
   }
    @Test
    public void confirmReceptionThrowsOutOfRangeCotizationError() throws Exception {
        doThrow(new UserValidation(DefinedError.OUT_OF_RANGE_COTIZATION.getErrorCode(),DefinedError.OUT_OF_RANGE_COTIZATION.getErrorMessage()))
                .when(userService).confirmReception(13,26);
        mockMvc.perform(put("/users/13/confirmReception/26")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(content().json("{'errorMessage': 'The price is below or above system cotization by more than 5%, the trading has been cancelled'}"));
    }
    @Test
    public void confirmReceptionTryWhenTransferNotConfirmedYet() throws Exception {
        doThrow(new UserValidation(DefinedError.FORBIDDEN_ACTION.getErrorCode(),"Transfer not confirmed yet"))
                .when(userService).confirmReception(13,26);
        mockMvc.perform(put("/users/13/confirmReception/26")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(content().json("{'errorMessage': 'Transfer not confirmed yet'}"));
    }

   @Test
   public void cancel() throws Exception {

       mockMvc.perform(put("/users/13/cancel/38")
               .contentType(MediaType.APPLICATION_JSON)
               .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isGone());
   }
    @Test
    public void userUnauthorizedToCancel() throws Exception {
        doThrow(new UserValidation(DefinedError.FORBIDDEN_ACTION.getErrorCode(), "User 13 not authorized to cancel the trading"))
                .when(userService).cancel(13,38);
        mockMvc.perform(put("/users/13/cancel/38")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(content().json("{'errorMessage': 'User 13 not authorized to cancel the trading'}"));
    }

}
