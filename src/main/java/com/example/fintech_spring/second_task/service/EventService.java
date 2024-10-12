package com.example.fintech_spring.second_task.service;

import com.example.fintech_spring.second_task.dto.Event;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface EventService {


    List<Event> getSuitableEvents(Double budget, String currency, LocalDate dateFrom, LocalDate dateTo) throws ExecutionException, InterruptedException;

    List<Event> getEvents(LocalDate dateFrom, LocalDate dateTo);

    Double getAmountInRub(Double budget, String fromCurrency);
}
