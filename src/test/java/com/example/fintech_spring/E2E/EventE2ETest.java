package com.example.fintech_spring.E2E;

import com.example.fintech_spring.dto.EventDto;
import com.example.fintech_spring.dto.entity.Event;
import com.example.fintech_spring.dto.entity.Location;
import com.example.fintech_spring.repository.LocationRepository;
import com.example.fintech_spring.service.event.EventService;
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

import java.sql.Timestamp;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EventE2ETest {

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16");

    @Autowired
    private EventService eventService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private LocationRepository locationRepository;

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
        eventService.deleteAll();
        locationRepository.deleteAll();
        Location location = new Location();
        location.setName("Test Location");
        location.setSlug("test-location");
        locationRepository.save(location);
    }


    @DynamicPropertySource
    static void setDataSourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create");
    }

    @Test
    public void testCreateAndRetrieveEvent() throws Exception {
        EventDto eventDto = new EventDto("Test Event", new Timestamp(1L), 10.0, 1);
        String eventJson = objectMapper.writeValueAsString(eventDto);

        mockMvc.perform(post("/api/v1/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(eventJson))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/events/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Event"))
                .andExpect(jsonPath("$.price").value(10.0));
    }

    @Test
    public void testUpdateAndRetrieveEvent() throws Exception {

        Location location = locationRepository.findAll().get(0);
        System.out.println(location.getId());

        EventDto eventDto = new EventDto("Test Event", new Timestamp(1L), 10.0, location.getId());
        eventService.save(eventDto);

        Event savedEvent = eventService.findAll().get(0);
        Integer savedEventId = savedEvent.getId();

        EventDto eventDtoUpdated = new EventDto("updated", new Timestamp(2), 7.0, location.getId());
        String eventJson = objectMapper.writeValueAsString(eventDtoUpdated);

        mockMvc.perform(put("/api/v1/events/{id}", savedEventId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(eventJson))
                .andExpect(status().isOk());
    }
}