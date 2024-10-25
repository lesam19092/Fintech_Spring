package com.example.fintech_spring.service;


import com.example.fintech_spring.data_source.Repository;
import com.example.fintech_spring.dto.Category;
import com.example.fintech_spring.dto.Location;
import com.example.fintech_spring.observer.DataObserver;
import com.example.fintech_spring.observer.DataSavingObserver;
import com.example.fintech_spring.observer.Publisher;
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


    private final Repository<Integer, Category> categoryRepository;

    private final Repository<UUID, Location> locationRepository;

    private final RestTemplate restTemplate;

    private final Publisher publisher;


    @EventListener(ApplicationReadyEvent.class)
    private void fetchingRepositories() {


        DataObserver observer = new DataSavingObserver();
        publisher.registerObserver(observer);

        log.info("Application starting, fetching categories...");
        fetchingCategories();

        publisher.unregisterObserver(observer);

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
                    .forEach(category -> categoryRepository.save(category.getId(), category));

            publisher.notifyObservers("Categories fetched");
          //  log.info("Successfully fetched and stored {} categories.", categoryRepository.getTotalCount());
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
                    .forEach(location -> {
                        location.setUuid(UUID.randomUUID());
                        locationRepository.save(location.getUuid(), location);
                    });
           // log.info("Successfully fetched and stored {} locations.", locationRepository.getTotalCount());
            publisher.notifyObservers("Locations fetched");
        } catch (Exception ex) {
            log.error("Error fetching categories:", ex);
        }
    }
}






