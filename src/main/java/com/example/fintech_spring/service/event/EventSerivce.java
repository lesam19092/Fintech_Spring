package com.example.fintech_spring.service.event;


import com.example.fintech_spring.dto.EventDto;

import java.time.LocalDate;
import java.util.List;

public interface EventSerivce {

    void save(EventDto eventDto);

    EventDto findById(Integer id);

    void deleteById(Integer id);

    void update(Integer id, EventDto eventDto);

    List<EventDto> findEventsByFilter(String title, String place, LocalDate dateFrom, LocalDate toDate);
}
