package com.example.fintech_spring.service;


import com.example.fintech_spring.dto.Category;
import com.example.fintech_spring.dto.Location;
import com.example.fintech_spring.komanda.Inserter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class KudoServiceImpl implements KudoService {


    private final Inserter inserter;

    private final RestTemplate restTemplate;


    @EventListener(ApplicationReadyEvent.class)
    private void fetchingRepositories() {
        log.info("Application starting, fetching categories...");
        fetchingCategories();

        log.info("Fetching locations...");
        fetchingLocations();
    }

    @Override
    public void fetchingCategories() {
        try {
            ResponseEntity<List<Category>> rateResponse =
                    restTemplate.exchange("/place-categories",
                            HttpMethod.GET, null, new ParameterizedTypeReference<List<Category>>() {
                            });
            rateResponse.getBody()
                    .forEach(inserter::insertCategory);

            log.info("Successfully fetched and stored  categories.");
        } catch (Exception ex) {
            log.error("Error fetching categories:", ex);
        }
    }

    @Override
    public void fetchingLocations() {
        try {
            ResponseEntity<List<Location>> rateResponse =
                    restTemplate.exchange("/locations",
                            HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                            });
            rateResponse.getBody()
                    .forEach(inserter::insertLocation);
            log.info("Successfully fetched and stored locations.");
        } catch (Exception ex) {
            log.error("Error fetching categories:", ex);
        }
    }
}






