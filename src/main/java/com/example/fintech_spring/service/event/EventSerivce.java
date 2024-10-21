package com.example.fintech_spring.service.event;


import com.example.fintech_spring.dto.EventDto;

public interface EventSerivce {

    void save(EventDto eventDto);

    EventDto findById(Integer id);

    void deleteById(Integer id);

    void update(Integer id, EventDto eventDto);
}
