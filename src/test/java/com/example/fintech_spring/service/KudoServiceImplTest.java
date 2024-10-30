package com.example.fintech_spring.service;

import com.example.fintech_spring.dto.Category;
import com.example.fintech_spring.dto.Location;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class KudoServiceImplTest {


    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void fetchingCategories() {

        ResponseEntity<List<Category>> response = restTemplate.exchange("https://kudago.com/public-api/v1.4/place-categories", HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });

        List<Category> category = response.getBody();

        assertThat(category, hasSize(54));

    }


    @Test
    void fetchingLocations() {
        ResponseEntity<List<Location>> response = restTemplate.exchange("https://kudago.com/public-api/v1.4/locations", HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });

        List<Location> locations = response.getBody();

        assertThat(locations, hasSize(5));
    }




}