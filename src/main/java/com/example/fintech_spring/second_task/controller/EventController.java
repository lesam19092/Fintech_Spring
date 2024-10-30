package com.example.fintech_spring.second_task.controller;


import com.example.fintech_spring.second_task.dto.Event;
import com.example.fintech_spring.second_task.dto.EventRequest;
import com.example.fintech_spring.second_task.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping("/events")
    public List<Event> getEvents(
            @RequestBody EventRequest eventRequest) throws ExecutionException, InterruptedException {
        return eventService.getSuitableEventsMono(eventRequest).block();
    }

}
