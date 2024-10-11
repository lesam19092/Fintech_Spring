package com.example.fintech_spring.controllers;


import com.example.fintech_spring.dto.Location;
import com.example.fintech_spring.service.controlling.LocationSerivice;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class LocationsController {

    private final LocationSerivice locationSerivice;


    @GetMapping(value = "/locations")
    public ResponseEntity<List<Location>> getAllLocations() {
        List<Location> locations = locationSerivice.findAll();
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }


    @GetMapping(value = "/locations/{id}")
    public ResponseEntity<Optional<Location>> getLocationById(@PathVariable UUID id) {
        Optional<Location> location = locationSerivice.findById(id);
        return new ResponseEntity<>(location, HttpStatus.OK);
    }


    @PostMapping(value = "/locations")
    public ResponseEntity<Location> createLocation(@RequestBody Location location) {
        locationSerivice.save(location.getUuid(), location);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PutMapping(value = "/locations/{id}")
    public ResponseEntity<?> updateLocation(@PathVariable UUID id, @RequestBody Location location) {
        locationSerivice.update(id, location);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/locations/{id}")
    public ResponseEntity<?> deleteLocation(@PathVariable UUID id) {
        boolean deleted = locationSerivice.deleteById(id);
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }
}

