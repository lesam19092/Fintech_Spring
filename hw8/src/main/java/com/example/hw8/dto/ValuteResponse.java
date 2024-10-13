package com.example.hw8.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValuteResponse {

    private String currency;
    private Double rate;
}
