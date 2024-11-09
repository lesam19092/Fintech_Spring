package com.example.fintech_spring.komanda;

import com.example.fintech_spring.data_source.Repository;
import com.example.fintech_spring.dto.Location;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
@Slf4j
public class InsertLocationCommand implements Command {

    private final Repository<UUID, Location> locationRepository;

    private final RestTemplate restTemplate;


    @Override
    public void execute() {
        try {
            ResponseEntity<List<Location>> rateResponse =
                    restTemplate.exchange("/locations",
                            HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                            });
            rateResponse.getBody()
                    .forEach(location -> locationRepository.save(UUID.randomUUID(), location));
            log.info("Successfully fetched and stored locations.");
            log.info("Total locations: {}", locationRepository.getTotalCount());
        } catch (Exception ex) {
            log.error("Error fetching categories:", ex);
        }
    }
}
