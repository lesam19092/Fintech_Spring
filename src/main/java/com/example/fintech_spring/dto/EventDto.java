package com.example.fintech_spring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class EventDto {
    @NotBlank
    private String title;
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}", message = "Invalid status date")
    private Timestamp date;
    @Positive
    private Double price;
    @Positive
    private Integer locationId;
}