package com.example.fintech_spring.service;


import com.example.fintech_spring.dto.Location;
import com.example.fintech_spring.service.location.LocationService;
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
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class KudoServiceImpl implements KudoService {


    private final RestTemplate restTemplate;
    private final LocationService locationService;


    //@EventListener(ApplicationReadyEvent.class)
    private void fetchingRepositories() {
        log.info("Fetching locations...");
        fetchingLocations();
    }

    @Override
    public void fetchingLocations() {
        try {
            ResponseEntity<List<Location>> rateResponse =
                    restTemplate.exchange("/locations",
                            HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                            });
            rateResponse.getBody()
                    .forEach(locationService::save);
            log.info("Successfully fetched and stored locations");
        } catch (Exception ex) {
            log.error("Error fetching categories:", ex);
        }
    }
}






