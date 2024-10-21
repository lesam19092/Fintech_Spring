package com.example.fintech_spring.controllers;

import com.example.fintech_spring.dto.EventDto;
import com.example.fintech_spring.service.event.EventSerivce;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class EventsController {

    private final EventSerivce eventSerivce;


    @GetMapping("/events/{id}")
    public EventDto getEventById(@PathVariable Integer id) {
        return eventSerivce.findById(id);
    }


    @PostMapping("/events")
    public void createEvent(@RequestBody EventDto eventDto) {
        eventSerivce.save(eventDto);
    }


    @PutMapping("/events/{id}")
    public void updateEvent(@PathVariable Integer id, @RequestBody EventDto eventDto) {
        eventSerivce.update(id, eventDto);
    }

    @DeleteMapping("/events/{id}")
    public void deleteEvent(@PathVariable Integer id) {
        eventSerivce.deleteById(id);
    }


}
