package com.example.fintech_spring.komanda;

import com.example.fintech_spring.data_source.Repository;
import com.example.fintech_spring.dto.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class InsertCategoryCommand implements Command {

    private final Repository<Integer, Category> categoryRepository;

    private final RestTemplate restTemplate;


    @Override
    public void execute() {

        try {
            ResponseEntity<List<Category>> rateResponse =
                    restTemplate.exchange("/place-categories",
                            HttpMethod.GET, null, new ParameterizedTypeReference<List<Category>>() {
                            });
            rateResponse.getBody()
                    .forEach(category -> categoryRepository.save(category.getId(), category));
            log.info("Successfully fetched and stored  categories.");
            log.info("Total categories: {}", categoryRepository.getTotalCount());
        } catch (Exception ex) {
            log.error("Error fetching categories:", ex);
        }

    }
}
