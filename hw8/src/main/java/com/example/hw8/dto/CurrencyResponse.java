package com.example.hw8.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CurrencyResponse {

    private String fromCurrencyCode;
    private String toCurrencyCode;
    private Double convertedAmount;
}
