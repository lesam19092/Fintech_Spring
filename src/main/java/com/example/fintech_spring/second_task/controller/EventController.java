package com.example.fintech_spring.second_task.controller;


import com.example.fintech_spring.second_task.client.ApiClient;
import com.example.fintech_spring.second_task.dto.Event;
import com.example.fintech_spring.second_task.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;



    @GetMapping("/events")
    public List<Event> getEvents(
            @RequestParam(name = "budget", required = true) @Min(1) Double budget, @RequestParam(name = "currency", required = true) String currency,
            @RequestParam(name = "dateFrom", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateFrom,
            @RequestParam(name = "dateTo", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateTo
    ) throws ExecutionException, InterruptedException {


        return eventService.getSuitableEventsMono(budget, currency, dateFrom, dateTo).block();
    }

}
