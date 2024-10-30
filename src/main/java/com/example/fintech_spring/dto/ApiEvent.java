package com.example.fintech_spring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class ApiEvent implements Serializable {

    @JsonProperty("results")
    private List<Event> results;

}