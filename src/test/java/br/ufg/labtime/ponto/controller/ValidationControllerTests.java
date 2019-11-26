package br.ufg.labtime.ponto.controller;

import br.ufg.labtime.ponto.PontoApplication;
import br.ufg.labtime.ponto.service.ModelMapperService;
import br.ufg.labtime.ponto.service.ValidationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureWebMvc
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:test.properties")
@ContextConfiguration(classes = PontoApplication.class)
public class ValidationControllerTests {

    @MockBean
    ValidationService validationService;

    @Autowired
    ModelMapperService modelMapperService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void UNIQUEEMAIL_it_should_return_true_when_unique() throws Exception {
        when(validationService.uniqueEmail(any(String.class))).thenReturn(true);
        mockMvc.perform(get("/validation/unique-email/email@provider.com")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    public void UNIQUEEMAIL_it_should_return_false_when_not_unique() throws Exception {
        when(validationService.uniqueEmail(any(String.class))).thenReturn(false);
        mockMvc.perform(get("/validation/unique-email/email@provider.com")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(false));
    }
}
