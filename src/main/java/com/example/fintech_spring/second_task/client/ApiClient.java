package com.example.fintech_spring.second_task.client;

import com.example.fintech_spring.second_task.dto.Event;

import java.time.LocalDate;
import java.util.List;

public interface ApiClient {

    List<Event> getEvents(LocalDate dateFrom, LocalDate dateTo);

    Double getAmountInRub(Double budget, String fromCurrency);
}
