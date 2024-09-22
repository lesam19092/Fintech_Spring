package com.example.fintech_spring.service;


import com.example.fintech_spring.data_source.Repository;
import com.example.fintech_spring.dto.Category;
import com.example.fintech_spring.dto.Location;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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


    private final Repository<Integer, Category> dbCategory;

    private final Repository<UUID, Location> dbLocation;

    private final RestTemplate restTemplate;


    @EventListener(ApplicationReadyEvent.class)
    private void fetchingStorages() {
        log.info("Application starting, fetching categories...");
        getCategories();

        log.info("Fetching locations...");
        getLocations();
    }

    @Override
    public void getCategories() {
        try {
            ResponseEntity<List<Category>> rateResponse =
                    restTemplate.exchange("/place-categories",
                            HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                            });
            rateResponse.getBody()
                    .forEach(category -> dbCategory.save(category.getId(), category));
            log.info("Successfully fetched and stored {} categories.", dbCategory.getTotalCount());
        } catch (Exception ex) {
            log.error("Error fetching categories:", ex);
        }
    }

    @Override
    public void getLocations() {
        try {
            ResponseEntity<List<Location>> rateResponse =
                    restTemplate.exchange("/locations",
                            HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                            });


            rateResponse.getBody()
                    .forEach(location -> {
                        location.setUuid(UUID.randomUUID());
                        dbLocation.save(location.getUuid(), location);
                    });
            log.info("Successfully fetched and stored {} locations.", dbLocation.getTotalCount());
        } catch (Exception ex) {
            log.error("Error fetching categories:", ex);
        }
    }
}






