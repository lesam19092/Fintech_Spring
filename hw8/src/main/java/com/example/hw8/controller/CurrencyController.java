package com.example.hw8.controller;

import com.example.hw8.dto.CurrencyRequest;
import com.example.hw8.dto.CurrencyResponse;
import com.example.hw8.dto.ValuteResponse;
import com.example.hw8.service.CurrencyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyService currencyService;


    @GetMapping("/currencies/rate/{code}")
    public ValuteResponse getCurrency(@PathVariable String code) {

        return currencyService.getCurrencyByCode(code);
    }

    @PostMapping("currencies/convert")
    public CurrencyResponse convertCurrency(@RequestBody @Valid CurrencyRequest currencyRequest) {

        return currencyService.convertCurrency(currencyRequest);
    }

}
