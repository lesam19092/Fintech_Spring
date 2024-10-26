package com.example.fintech_spring.second_task.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CurrencyRequest {
    private String fromCurrency;

    private final String toCurrency = "RUB";

    private Double amount;

}
