package com.example.fintech_spring.second_task.service;

import com.example.fintech_spring.second_task.client.ApiClient;
import com.example.fintech_spring.second_task.client.ReactiveClient;
import com.example.fintech_spring.second_task.dto.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {


    //ЗДЕСЬ Я СДЕЛАЛ ПЕРЕКЛЮЧЕНИЕ ЧЕРЕЗ ReactiveClient/RessTemplateClient
    //через аннотацию ConditionalOnProperty
    private final ApiClient apiClient;

    @Qualifier("executorServiceForFetchingData")
    private final ExecutorService executorServiceForFetchingData;


    @Override
    public List<Event> getSuitableEvents(Double budget, String currency, LocalDate dateFrom, LocalDate dateTo) throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();
        log.info("Starting to get events and convert budget...");

        CompletableFuture<List<Event>> listOfEvents = CompletableFuture.supplyAsync(() -> apiClient.getEvents(dateFrom, dateTo), executorServiceForFetchingData);
        CompletableFuture<Double> amountOfMoney = CompletableFuture.supplyAsync(() -> apiClient.getAmountInRub(budget, currency), executorServiceForFetchingData);

        listOfEvents.thenAcceptBoth(amountOfMoney, (events, budgetInRub) -> {
            log.info("Filtering events based on budget: {}", budgetInRub);
            events.removeIf(event -> {
                try {
                    double price = Double.parseDouble(event.getPrice());
                    return price > budgetInRub;
                } catch (NumberFormatException e) {
                    log.warn("Invalid price format for event: {}", event.getTitle(), e);
                    return true;
                }
            });
        });

        try {
            listOfEvents.get();
            amountOfMoney.get();
            long endTime = System.currentTimeMillis();
            log.debug("Completed in {} ms", (endTime - startTime));
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error during data processing", e);
        }
        return listOfEvents.get();

    }


    //with mono
    // не ругай меня здесь пожалуйста , я тут специально создам щас ReactiveClient
    // чтобы выполнить третий пункт дз

    private final ReactiveClient reactiveClient;

    public Mono<List<Event>> getSuitableEventsMono(Double budget, String currency, LocalDate dateFrom, LocalDate dateTo) {
        long startTime = System.currentTimeMillis();
        log.info("Starting to get events and convert budget...");
        Mono<List<Event>> eventsMono = reactiveClient.getEventsMono(dateFrom, dateTo);
        Mono<Double> budgetInRubMono = reactiveClient.getAmountInRubMono(budget, currency);
        return Mono.zip(eventsMono, budgetInRubMono)
                .map(tuple -> {
                    List<Event> events = tuple.getT1();
                    Double budgetInRub = tuple.getT2();
                    log.info("Filtering events based on budget: {}", budgetInRub);
                    return events.stream()
                            .filter(event -> {
                                try {
                                    double price = Double.parseDouble(event.getPrice());
                                    return price <= budgetInRub;
                                } catch (NumberFormatException e) {
                                    log.warn("Invalid price format for event: {}", event.getTitle(), e);
                                    return false;
                                }
                            })
                            .collect(Collectors.toList());
                })
                .doOnTerminate(() -> {
                    long endTime = System.currentTimeMillis();
                    log.info("Completed in {} ms", (endTime - startTime));
                });
    }
}