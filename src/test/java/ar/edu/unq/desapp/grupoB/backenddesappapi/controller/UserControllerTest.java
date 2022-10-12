package ar.edu.unq.desapp.grupoB.backenddesappapi.controller;

import ar.edu.unq.desapp.grupoB.backenddesappapi.model.DTO.CreateTransactionDTO;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.DTO.RegisterDTO;
import ar.edu.unq.desapp.grupoB.backenddesappapi.services.UserService;
import ar.edu.unq.desapp.grupoB.backenddesappapi.webservices.UserRestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = ar.edu.unq.desapp.grupoB.backenddesappapi.BackendDesappApiApplication.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void registerOk() throws Exception {

        RegisterDTO registerDTO = new RegisterDTO("Tobias","Torres","tobitest1@gmail.com","casalinda","1234","123456789","test");

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registerDTO.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllUser() throws Exception{

        mockMvc.perform(get("/users/allUser")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void openTradingOk() throws Exception {

        CreateTransactionDTO createTransactionDTO = new CreateTransactionDTO(4,20.00,222.2,200.0);

        mockMvc.perform(post("/users/{id}/newTrading")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createTransactionDTO.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }


}
