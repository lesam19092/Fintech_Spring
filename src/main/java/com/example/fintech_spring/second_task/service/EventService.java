package com.example.fintech_spring.second_task.service;

import com.example.fintech_spring.second_task.dto.Event;
import com.example.fintech_spring.second_task.dto.EventRequest;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface EventService {


    List<Event> getSuitableEvents(EventRequest eventRequest) throws ExecutionException, InterruptedException;

    Mono<List<Event>> getSuitableEventsMono(EventRequest eventRequest);


}
