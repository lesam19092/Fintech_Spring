package com.example.hw8.service;

import com.example.hw8.dto.CurrencyRequest;
import com.example.hw8.dto.CurrencyResponse;
import com.example.hw8.dto.ValuteResponse;

public interface CurrencyService {


    ValuteResponse getCurrencyByCode(String code);

    CurrencyResponse convertCurrency(CurrencyRequest currencyRequest);
}
