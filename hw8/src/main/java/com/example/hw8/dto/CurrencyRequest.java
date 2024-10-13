package com.example.hw8.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CurrencyRequest {
    @NotBlank(message = "From currency cannot be blank")
    private String fromCurrency;

    @NotBlank(message = "To currency cannot be blank")
    private String toCurrency;

    @Positive(message = "Amount must be positive")
    private Double amount;

}
