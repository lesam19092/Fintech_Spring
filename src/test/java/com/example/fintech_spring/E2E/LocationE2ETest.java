package com.example.fintech_spring.E2E;


import com.example.fintech_spring.dto.LocationDto;
import com.example.fintech_spring.dto.entity.Event;
import com.example.fintech_spring.dto.entity.Location;
import com.example.fintech_spring.repository.EventRepository;
import com.example.fintech_spring.service.event.EventService;
import com.example.fintech_spring.service.location.LocationService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class LocationE2ETest {

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16");

    @Autowired
    private LocationService locationService;


    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @BeforeEach
    void setUp() {
        locationService.deleteAll();
    }

    @DynamicPropertySource
    static void setDataSourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create");
    }

    @Test
    public void testCreateAndRetrieveLocation() throws Exception {


        LocationDto locationDto = new LocationDto("Test Location", "test-location", null);
        String locationJson = objectMapper.writeValueAsString(locationDto);

        mockMvc.perform(post("/api/v1/locations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(locationJson))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/locations/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Location"))
                .andExpect(jsonPath("$.slug").value("test-location"));
    }


    @Test
    public void testUpdateAndRetrieveLocation() throws Exception {
        LocationDto locationDto = new LocationDto("Test Location", "test-location", List.of(1));
        locationService.save(locationDto);

        Location savedLocation = locationService.findAll().get(0);
        Integer savedLocationId = savedLocation.getId();

        LocationDto locationDtoUpdated = new LocationDto("updated", "Up-dated", null);
        String locationJson = objectMapper.writeValueAsString(locationDtoUpdated);

        mockMvc.perform(put("/api/v1/locations/{id}", savedLocationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(locationJson))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/locations/{id}", savedLocationId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("updated"))
                .andExpect(jsonPath("$.slug").value("Up-dated"));
    }

}