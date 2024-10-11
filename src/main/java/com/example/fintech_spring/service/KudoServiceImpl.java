package com.example.fintech_spring.service;


import com.example.fintech_spring.data_source.Repository;
import com.example.fintech_spring.dto.Category;
import com.example.fintech_spring.dto.Location;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class KudoServiceImpl implements KudoService {


    private final Repository<Integer, Category> categoryRepository;

    private final Repository<UUID, Location> locationRepository;

    private final RestTemplate restTemplate;

    @Qualifier("executorServiceForFetchingData")
    private final ExecutorService executorServiceForFetchingData;

    @Qualifier("executorSheduled")
    private final ScheduledExecutorService executorSheduled;

    @Value("${duration.delay}")
    private int delay;


    @EventListener(ApplicationStartedEvent.class)
    private void scheduleDataInitialization() {


        executorSheduled.scheduleAtFixedRate(this::initializeData, 0, delay, TimeUnit.SECONDS);
    }

    private void initializeData() {
        long startTime = System.currentTimeMillis();
        log.info("Starting data initialization...");

        CompletableFuture<Void> categoriesFuture = CompletableFuture.runAsync(this::fetchingCategories, executorServiceForFetchingData);
        CompletableFuture<Void> locationsFuture = CompletableFuture.runAsync(this::fetchingLocations, executorServiceForFetchingData);

        CompletableFuture<Void> allOf = CompletableFuture.allOf(categoriesFuture, locationsFuture);
        try {
            allOf.get();
            long endTime = System.currentTimeMillis();


            log.info("Data initialization completed in {} ms", (endTime - startTime));
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error during data initialization", e);
        }

    }


    @Override
    public void fetchingCategories() {

        categoryRepository.clear();

        try {
            ResponseEntity<List<Category>> rateResponse =
                    restTemplate.exchange("/place-categories",
                            HttpMethod.GET, null, new ParameterizedTypeReference<List<Category>>() {
                            });
            rateResponse.getBody()
                    .forEach(category -> categoryRepository.save(category.getId(), category));
            log.info("Successfully fetched and stored {} categories.", categoryRepository.getTotalCount());
        } catch (Exception ex) {
            log.error("Error fetching categories:", ex);
        }
    }

    @Override
    public void fetchingLocations() {

        locationRepository.clear();

        try {
            ResponseEntity<List<Location>> rateResponse =
                    restTemplate.exchange("/locations",
                            HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                            });
            rateResponse.getBody()
                    .forEach(location -> {
                        location.setUuid(UUID.randomUUID());
                        locationRepository.save(location.getUuid(), location);
                    });
            log.info("Successfully fetched and stored {} locations.", locationRepository.getTotalCount());
        } catch (Exception ex) {
            log.error("Error fetching categories:", ex);
        }
    }
}






