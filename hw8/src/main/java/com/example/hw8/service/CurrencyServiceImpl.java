package com.example.hw8.service;


import com.example.hw8.dto.*;
import com.example.hw8.exception.CurrencyExistsButNotFoundException;
import com.example.hw8.exception.InvalidCurrencyCodeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;

import java.util.Currency;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrencyServiceImpl implements CurrencyService {

    private final RestClient restClient;


    @Override
    public ValuteResponse getCurrencyByCode(String code) {

        if (isValidCurrency(code)) {
            throw new InvalidCurrencyCodeException("Invalid currency code");
        }

        ListValute listValute = getListValutes();

        Valute valute = getValuteByCode(listValute, code);

        return ValuteResponse.builder()
                .currency(valute.getCharCode())
                .rate(Double.valueOf(valute.getValue())).build();
    }

    @Override
    public CurrencyResponse convertCurrency(CurrencyRequest currencyRequest) {


        String fromCurrency = currencyRequest.getFromCurrency();
        String toCurrency = currencyRequest.getToCurrency();
        Double amount = currencyRequest.getAmount();

        if (isValidCurrency(fromCurrency) || isValidCurrency(toCurrency)) {
            throw new InvalidCurrencyCodeException("Invalid currency code");
        }

        ValuteResponse fromCurrencyRate = getCurrencyByCode(fromCurrency);
        ValuteResponse toCurrencyRate = getCurrencyByCode(toCurrency);

        Double convertedAmount = (amount / toCurrencyRate.getRate()) * fromCurrencyRate.getRate();

        return CurrencyResponse.builder()
                .fromCurrencyCode(fromCurrency)
                .toCurrencyCode(toCurrency)
                .convertedAmount(convertedAmount)
                .build();


    }


    private ListValute getListValutes() {
        ListValute listValute = restClient.get().uri("/XML_daily.asp/")
                .accept(MediaType.APPLICATION_XML)
                .retrieve()
                .toEntity(ListValute.class)
                .getBody();
        if (listValute == null) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "ЦБ не отвечает , попробуйте заново через час ");
        }
        Valute newValute = new Valute();
        newValute.setValue("1.0");
        newValute.setCharCode("RUB");
        listValute.getValutes().add(newValute);

        return listValute;
    }


    private Valute getValuteByCode(ListValute listValute, String code) {

        Optional<Valute> optionalValute = listValute.getValutes().stream()
                .filter(val -> val.getCharCode().equals(code))
                .map(val -> {
                    val.setValue(val.getValue().replace(",", "."));
                    return val;
                })
                .findFirst();

        return checkCurrencyExist(optionalValute);

    }

    private boolean isValidCurrency(String code) {
        try {
            Currency.getInstance(code);
            return false;
        } catch (IllegalArgumentException e) {
            return true;
        }
    }

    private Valute checkCurrencyExist(Optional<Valute> valute) {
        if (valute.isPresent()) {
            return valute.get();
        }
        throw new CurrencyExistsButNotFoundException("валюта существует , но в цб ее нет");
    }
}

