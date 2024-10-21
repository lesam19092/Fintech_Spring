package com.example.fintech_spring.controllers;

import com.example.fintech_spring.dto.EventDto;
import com.example.fintech_spring.service.event.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class EventsController {

    private final EventService eventService;

    @GetMapping("/events/{id}")
    public EventDto getEventById(@PathVariable Integer id) {
        return eventService.findById(id);
    }


    @PostMapping("/events")
    public void createEvent(@RequestBody EventDto eventDto) {
        eventService.save(eventDto);
    }


    @PutMapping("/events/{id}")
    public void updateEvent(@PathVariable Integer id, @RequestBody EventDto eventDto) {
        eventService.update(id, eventDto);
    }

    @DeleteMapping("/events/{id}")
    public void deleteEvent(@PathVariable Integer id) {
        eventService.deleteById(id);
    }


    @GetMapping("/events/filter")
    public List<EventDto> getEvents(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "place", required = false) String place,
            @RequestParam(value = "fromDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateFrom,
            @RequestParam(value = "toDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate

    ) {
        return eventService.findEventsByFilter(title, place, dateFrom, toDate);
    }
}
