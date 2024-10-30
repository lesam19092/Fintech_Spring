package com.example.fintech_spring.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class LocationDto {
    @NotBlank
    private String slug;
    @NotBlank
    private String name;
    private List<Integer> eventIds;
}
