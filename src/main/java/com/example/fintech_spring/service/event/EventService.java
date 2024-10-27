package com.example.fintech_spring.service.event;


import com.example.fintech_spring.dto.EventDto;
import com.example.fintech_spring.dto.entity.Event;

import java.time.LocalDate;
import java.util.List;

public interface EventService {

    void save(EventDto eventDto);

    EventDto findById(Long id);

    void deleteById(Long id);

    void update(Long id, EventDto eventDto);

    List<EventDto> findEventsByFilter(String title, String place, LocalDate dateFrom, LocalDate toDate);

    void deleteAll();

    List<Event> findAll();
}
