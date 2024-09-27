package com.example.fintech_spring.controllers;

import com.example.fintech_spring.dto.Location;
import com.example.fintech_spring.service.LocationSerivice;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = {LocationsController.class})
class LocationsControllerTest {


    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private LocationSerivice locationSerivice;
    private static ObjectMapper mapper = new ObjectMapper();


    @Test
    void getAllLocations() throws Exception {


        List<Location> locations = new ArrayList<>();

        locations.add(new Location(UUID.randomUUID(), "slug1", "name1"));
        locations.add(new Location(UUID.randomUUID(), "slug2", "name2"));

        when(locationSerivice.findAll()).thenReturn(locations);


        mockMvc.perform(get("/api/v1/locations"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].name", Matchers.equalTo("name1")));
    }

    @Test
    void getLocationById() throws Exception {


        var id = UUID.randomUUID();

        Location location = new Location(id, "slug1", "name1");

        String expectedJson = mapper
                .writeValueAsString(new Location(id, "slug1", "name1"));

        when(locationSerivice.findById(id)).thenReturn(Optional.of(location));

        mockMvc.perform(get("/api/v1/locations/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));

    }

    @Test
    void createLocation() throws Exception {

        String expectedJson = mapper.writeValueAsString(new Location(UUID.randomUUID(), "slug1", "name1"));


        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/locations")
                        .content(expectedJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void updateLocation() throws Exception {

        var id = UUID.randomUUID();

        String expectedJson = mapper.writeValueAsString(new Location(id, "slug1", "name1"));
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/locations/{id}", id)
                        .content(expectedJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void deleteLocation() throws Exception {
        var id = UUID.randomUUID();
        when(locationSerivice.deleteById(UUID.randomUUID())).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/locations/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}