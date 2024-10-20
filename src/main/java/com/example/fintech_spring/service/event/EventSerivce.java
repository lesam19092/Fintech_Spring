package com.example.fintech_spring.service.event;


import com.example.fintech_spring.dto.Event;

public interface EventSerivce {

    void save(Event event);

    Event findById(Integer id);

    void deleteById(Integer id);

    void update(Event event);
}
