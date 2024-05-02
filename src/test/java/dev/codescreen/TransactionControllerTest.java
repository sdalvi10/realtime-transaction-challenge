package dev.codescreen;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.codescreen.models.Amount;
import dev.codescreen.models.Request;
import dev.codescreen.persist.UserEntityRepository;
import dev.codescreen.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@EnableAutoConfiguration(exclude = {KafkaAutoConfiguration.class})
class TransactionControllerTest {

    @Autowired
    MockMvc mockMvc;
    @InjectMocks
    TransactionService service;

    @Mock
    UserEntityRepository userEntityRepository;

    @Autowired
    UserEntityRepository userEntityRepository1;

    @MockBean
    KafkaTemplate<String, Object> kafkaTemplate;

    @Test
    void ping() throws Exception {
        this.mockMvc.perform(get("/ping")).andExpect(status().isOk());
    }

    @Test
    void loadAmount() throws Exception {

        Request request = new Request(
                "55210c62-e480-asdf-bc1b-e991ac67FSAC",
                "8786e2f9-d472-46a8-958f-d659880e723d",
                new Amount(
                        "1000",
                        "USD",
                        "CREDIT"));

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(request);

        this.mockMvc.perform(put("/load/55210c62-e480-asdf-bc1b-e991ac67FSAC")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.messageId").value("55210c62-e480-asdf-bc1b-e991ac67FSAC"))
                .andExpect(jsonPath("$.userId").value("8786e2f9-d472-46a8-958f-d659880e723d"))
                .andExpect(jsonPath("$.balance.amount").value("6000.00"))
                .andExpect(jsonPath("$.balance.currency").value("USD"))
                .andExpect(jsonPath("$.balance.debitOrCredit").value("CREDIT"));
    }

    @Test
    void unloadAmount() throws Exception {
        Request request = new Request(
                "55210c62-e480-asdf-bc1b-e991ac67FSAC",
                "8786e2f9-d472-46a8-958f-d659880e723d",
                new Amount(
                        "1000",
                        "USD",
                        "DEBIT"));

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(request);

        this.mockMvc.perform(put("/authorization/55210c62-e480-asdf-bc1b-e991ac67FSwC")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.messageId").value("55210c62-e480-asdf-bc1b-e991ac67FSwC"))
                .andExpect(jsonPath("$.userId").value("8786e2f9-d472-46a8-958f-d659880e723d"))
                .andExpect(jsonPath("$.responseCode").value("APPROVED"))
                .andExpect(jsonPath("$.balance.amount").value("5000.00"))
                .andExpect(jsonPath("$.balance.currency").value("USD"))
                .andExpect(jsonPath("$.balance.debitOrCredit").value("DEBIT"));
    }
}
