package com.example.fintech_spring.controllers;


import com.example.fintech_spring.aspect.LogExecutionTime;
import com.example.fintech_spring.data_source.Repository;
import com.example.fintech_spring.dto.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@LogExecutionTime
@RequestMapping("/api/v1")
public class LocationsController {

    private final Repository<UUID, Location> locationRepository;


    @GetMapping(value = "/locations")
    public ResponseEntity<List<Location>> getAllLocations() {
        List<Location> locations = locationRepository.findAll();
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }


    @GetMapping(value = "/locations/{id}")
    public ResponseEntity<Optional<Location>> getLocationById(@PathVariable UUID id) {
        Optional<Location> location = locationRepository.findById(id);
        return new ResponseEntity<>(location, HttpStatus.OK);
    }


    @PostMapping(value = "/locations")
    public ResponseEntity<Location> createLocation(@RequestBody Location location) {
        locationRepository.save(location.getUuid(), location);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PutMapping(value = "/locations/{id}")
    public ResponseEntity<?> updateLocation(@PathVariable UUID id, @RequestBody Location location) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/locations/{id}")
    public ResponseEntity<?> deleteLocation(@PathVariable UUID id) {
        boolean deleted = locationRepository.deleteById(id);
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }
}

