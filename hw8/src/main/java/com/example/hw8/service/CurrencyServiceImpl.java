package com.example.hw8.service;

import com.example.hw8.client.ValuteClient;
import com.example.hw8.dto.*;
import com.example.hw8.exception.CurrencyExistsButNotFoundException;
import com.example.hw8.exception.InvalidCurrencyCodeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Currency;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrencyServiceImpl implements CurrencyService {

    private final ValuteClient valuteClient;

    @Override
    @CacheEvict(value = "valutes", allEntries = true)
    @Scheduled(fixedRateString = "${caching.spring.valuteListTTL}")
    public ValuteResponse getCurrencyByCode(String code) {

        if (isValidCurrency(code)) {
            log.error("Invalid currency code: {}", code);
            throw new InvalidCurrencyCodeException("Invalid currency code");
        }

        ListValute listValute = valuteClient.getListValutes();
        log.info("Fetched list of valutes: {}", listValute);

        Valute valute = getValuteByCode(listValute, code);
        log.info("Fetched valute by code {}: {}", code, valute);

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
            log.error("Invalid currency code: fromCurrency={}, toCurrency={}", fromCurrency, toCurrency);
            throw new InvalidCurrencyCodeException("Invalid currency code");
        }

        ValuteResponse fromCurrencyRate = getCurrencyByCode(fromCurrency);

        ValuteResponse toCurrencyRate = getCurrencyByCode(toCurrency);

        Double convertedAmount = (amount / toCurrencyRate.getRate()) * fromCurrencyRate.getRate();
        log.info("Converted amount: {} {} to {} = {}", amount, fromCurrency, toCurrency, convertedAmount);

        return CurrencyResponse.builder()
                .fromCurrencyCode(fromCurrency)
                .toCurrencyCode(toCurrency)
                .convertedAmount(convertedAmount)
                .build();
    }

    private Valute getValuteByCode(ListValute listValute, String code) {
        Optional<Valute> optionalValute = listValute.getValutes().stream()
                .filter(val -> val.getCharCode().equals(code))
                .peek(val -> val.setValue(val.getValue().replace(",", ".")))
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