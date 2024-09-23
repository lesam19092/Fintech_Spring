package com.example.fintech_spring.controllers;

//TODO(REFACTOR)

import com.example.fintech_spring.data_source.Repository;
import com.example.fintech_spring.dto.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
public class LocationsController {

    private final Repository<UUID, Location> locationRepository;


    @GetMapping(value = "/api/v1/locations")
    public ResponseEntity<List<Location>> getAllLocations() {
        List<Location> locations = locationRepository.findAll();
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }


    @GetMapping(value = "/api/v1/locations/{id}")
    public ResponseEntity<Location> getLocationById(@PathVariable UUID id) {
        Location location = locationRepository.findById(id);
        return new ResponseEntity<>(location, HttpStatus.OK);
    }


    @PostMapping(value = "/api/v1/locations")
    public ResponseEntity<Location> createLocation(@RequestBody Location location) {
        locationRepository.save(location.getUuid(), location);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PutMapping(value = "/api/v1/locations/{id}")
    public ResponseEntity<?> updateLocation(@PathVariable UUID id, @RequestBody Location location) {

        boolean updated = locationRepository.update(id, location);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/v1/locations/{id}")
    public ResponseEntity<?> deleteLocation(@PathVariable UUID id) {
        boolean deleted = locationRepository.deleteById(id);
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }
}

