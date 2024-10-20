package com.example.fintech_spring.controllers;

import com.example.fintech_spring.dto.Event;
import com.example.fintech_spring.service.event.EventSerivce;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class EventsController {

    private final EventSerivce eventSerivce;


    @GetMapping("/events/{id}")
    public Event getEventById(@PathVariable Integer id) {
        return eventSerivce.findById(id);
    }


    @PostMapping("/events")
    public void createEvent(@RequestBody Event event) {
        eventSerivce.save(event);
    }


    @PutMapping("/events")
    public void updateEvent(@RequestBody Event event) {
        eventSerivce.update(event);
    }

    @DeleteMapping("/events/{id}")
    public void deleteEvent(@PathVariable Integer id) {
        eventSerivce.deleteById(id);
    }


}
