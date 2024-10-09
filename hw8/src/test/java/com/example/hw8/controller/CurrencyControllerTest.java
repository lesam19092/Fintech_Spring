package com.example.hw8.controller;

import com.example.hw8.dto.CurrencyRequest;
import com.example.hw8.dto.CurrencyResponse;
import com.example.hw8.dto.ValuteResponse;
import com.example.hw8.exception.CurrencyExistsButNotFoundException;
import com.example.hw8.exception.ErrorResponse;
import com.example.hw8.exception.InvalidCurrencyCodeException;
import com.example.hw8.service.CurrencyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = {CurrencyController.class})
class CurrencyControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CurrencyService currencyService;
    private static ObjectMapper mapper = new ObjectMapper();


    //тест проверки получения валюты , Которая есть в цб
    @Test
    void getCurrencyByValidCode() throws Exception {

        ValuteResponse valuteResponse =
                ValuteResponse.builder().currency("USD").rate(100.0).build();

        String expectedJson = mapper
                .writeValueAsString(valuteResponse);

        when(currencyService.getCurrencyByCode("USD")).thenReturn(valuteResponse);

        mockMvc.perform(get("/currencies/rate/{code}", "USD"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));


    }


    //тест проверки получения валюты которая сущесвует , но ее нет в цб
    @Test
    void getCurrencyByValidCodeButDoesntExistInCB() throws Exception {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage("валюта существует , но в цб ее нет");

        String expectedJson = mapper
                .writeValueAsString(errorResponse);

        when(currencyService.getCurrencyByCode("UGX"))
                .thenThrow(new CurrencyExistsButNotFoundException("валюта существует , но в цб ее нет"));

        mockMvc.perform(get("/currencies/rate/{code}", "UGX"))
                .andExpect(status().is4xxClientError())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));


    }

    //тест проверки получения валюты которой вообще нет
    @Test
    void getCurrencyByUnValidCode() throws Exception {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage("Invalid currency code");

        String expectedJson = mapper
                .writeValueAsString(errorResponse);

        when(currencyService.getCurrencyByCode("UGX"))
                .thenThrow(new InvalidCurrencyCodeException("Invalid currency code"));

        mockMvc.perform(get("/currencies/rate/{code}", "UGX"))
                .andExpect(status().is4xxClientError())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));


    }


    //тест проверки конвертации валюты , когда все корректно
    @Test
    void convertCurrencyWhenAllIsValid() throws Exception {
        CurrencyRequest currencyRequest = CurrencyRequest.builder()
                .fromCurrency("USD")
                .toCurrency("RUB")
                .amount(100.0)
                .build();


        CurrencyResponse currencyResponse = CurrencyResponse.builder()
                .fromCurrencyCode("USD")
                .toCurrencyCode("RUB")
                .convertedAmount(9694.83)
                .build();

        String expectedJson = mapper.writeValueAsString(currencyRequest);

        String resultJson = mapper.writeValueAsString(currencyResponse);

        when(currencyService.convertCurrency(currencyRequest))
                .thenReturn(currencyResponse);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/currencies/convert")
                        .content(expectedJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(resultJson));

    }
    @Test
    void convertCurrencyWhenAmountLess0() throws Exception {
        CurrencyRequest currencyRequest = CurrencyRequest.builder()
                .fromCurrency("USD")
                .toCurrency("RUB")
                .amount(-100.0)
                .build();



        String expectedJson = mapper.writeValueAsString(currencyRequest);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/currencies/convert")
                        .content(expectedJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }
    @Test
    void convertCurrencyWhenOneFieldIsNull() throws Exception {
        CurrencyRequest currencyRequest = CurrencyRequest.builder()
              //  .fromCurrency("USD") null
                .toCurrency("RUB")
                .amount(100.0)
                .build();



        String expectedJson = mapper.writeValueAsString(currencyRequest);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/currencies/convert")
                        .content(expectedJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }
}