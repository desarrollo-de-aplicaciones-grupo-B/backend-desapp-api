package ar.edu.unq.desapp.grupoB.backenddesappapi.controller;

import ar.edu.unq.desapp.grupoB.backenddesappapi.model.DTO.CreateTransactionDTO;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.DTO.RegisterDTO;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Utils.security.JwtRequest;
import ar.edu.unq.desapp.grupoB.backenddesappapi.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;


import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = ar.edu.unq.desapp.grupoB.backenddesappapi.BackendDesappApiApplication.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

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
        RegisterDTO registerDTO = new RegisterDTO("TobiTesft33d14331","Torres","tobitefs2222t1433d24321@gmail.com","casalinda","1234","123456789","test");

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registerDTO.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(userService).save(ArgumentMatchers.refEq(registerDTO));
    }

    @Test
    public void registerFailsTest() throws Exception{
        RegisterDTO badEmailUser =anyUser();
        badEmailUser.setEmail("email@@bad64&-com");
        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(anyUser().toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

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
                .andExpect(status().isOk());
        verify(userService).openTrading(eq(1), ArgumentMatchers.refEq((anyTransaction())));
    }

    @Test
    public void buyTradingOk() throws Exception {
        mockMvc.perform(put("/users/14/buy/27")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

   @Test
   public void confirmTransferOk() throws Exception {
      mockMvc.perform(put("/users/14/confirmTransfer/27")
              .contentType(MediaType.APPLICATION_JSON)
              .accept(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk());
   }

   @Test
   public void confirmReceptionOk() throws Exception {

       mockMvc.perform(put("/users/13/confirmReception/26")
               .contentType(MediaType.APPLICATION_JSON)
               .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
   }

   @Test
   public void cancel() throws Exception {

       mockMvc.perform(put("/users/13/cancel/38")
               .contentType(MediaType.APPLICATION_JSON)
               .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
   }

}
