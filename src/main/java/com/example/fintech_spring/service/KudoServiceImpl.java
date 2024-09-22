package com.example.fintech_spring.service;


import com.example.fintech_spring.data_source.SimpleStorage;
import com.example.fintech_spring.dto.Category;
import com.example.fintech_spring.dto.Location;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class KudoServiceImpl implements KudoService {


    private final SimpleStorage<Integer, Category> dbCategory;

    private final SimpleStorage<UUID, Location> dbLocation;

    private final RestTemplate restTemplate = new RestTemplate();


    @Autowired
    public KudoServiceImpl(SimpleStorage<Integer, Category> dbCategory, SimpleStorage<UUID, Location> dbLocation) {
        this.dbCategory = dbCategory;
        this.dbLocation = dbLocation;
    }

    @Override
    @Order(1)
    @EventListener(ApplicationReadyEvent.class)
    public void getCategories() {
//TODO (спрятать ссылки в application yaml)

        String baseUrl =
                "https://kudago.com/public-api/v1.4/place-categories";

        try {
            ResponseEntity<List<Category>> rateResponse =
                    restTemplate.exchange(baseUrl,
                            HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                            });
            List<Category> categories = rateResponse.getBody();

            categories
                    .forEach(category -> dbCategory.put(category.getId(), category));

            System.out.println(dbCategory.getStorage().size());
        } catch (NullPointerException ex) {
            log.info(ex.getMessage());
        }


    }

    @Override
    @Order(2)

    @EventListener(ApplicationReadyEvent.class)
    public void getLocations() {
        log.info("я выполни подгрузку локаций");


        String baseUrl =
                "https://kudago.com/public-api/v1.4/locations";

        try {


            ResponseEntity<List<Location>> rateResponse =
                    restTemplate.exchange(baseUrl,
                            HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                            });
            List<Location> locations = rateResponse.getBody();


            locations
                    .forEach(category -> dbLocation.put(UUID.randomUUID(), category));
            System.out.println(dbLocation.getStorage().size());
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }

    }
}

//TODO(REFACTOR)





