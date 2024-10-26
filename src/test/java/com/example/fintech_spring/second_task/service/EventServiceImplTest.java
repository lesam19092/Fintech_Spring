package com.example.fintech_spring.second_task.service;


import com.example.fintech_spring.second_task.client.ApiClient;
import com.example.fintech_spring.second_task.client.ReactiveClient;
import com.example.fintech_spring.second_task.dto.Event;
import com.example.fintech_spring.second_task.dto.EventRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class EventServiceImplTest {

    @Mock
    private ReactiveClient apiClient;


    @InjectMocks
    private EventServiceImpl eventService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetSuitableEventsMono() {
        EventRequest request = EventRequest.builder()
                .dateFrom(LocalDate.parse("2023-01-01"))
                .dateTo(LocalDate.parse("2023-12-31"))
                .budget(1000.0)
                .currency("USD")
                .build();

        Event event1 = new Event();
        event1.setPrice("500");
        Event event2 = new Event();
        event2.setPrice("1500");

        List<Event> events = Arrays.asList(event1, event2);
        Mono<List<Event>> eventsMono = Mono.just(events);
        Mono<Double> budgetMono = Mono.just(1000.0);

        when(apiClient.getEventsMono(any(), any())).thenReturn(eventsMono);
        when(apiClient.getAmountInRubMono(any(), any())).thenReturn(budgetMono);

        List<Event> result = eventService.getSuitableEventsMono(request).block();

        assertEquals(1, result.size());
        assertEquals("500", result.get(0).getPrice());
    }
}