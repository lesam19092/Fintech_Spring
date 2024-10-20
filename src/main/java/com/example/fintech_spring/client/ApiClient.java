package com.example.fintech_spring.client;


import com.example.fintech_spring.dto.Event;

import java.time.LocalDate;
import java.util.List;

public interface ApiClient {

    List<Event> getEvents(LocalDate dateFrom, LocalDate dateTo);

}