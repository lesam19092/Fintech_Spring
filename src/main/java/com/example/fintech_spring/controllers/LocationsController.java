package com.example.fintech_spring.controllers;


import com.example.fintech_spring.dto.Location;
import com.example.fintech_spring.service.location.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class LocationsController {

    private final LocationService locationService;


    @GetMapping("/locations/{id}")
    public Location getLocationById(@PathVariable Integer id) {
        return locationService.findById(id);
    }


    @PostMapping("/locations")
    public void createLocation(@RequestBody Location location) {
        locationService.save(location);
    }


    @PutMapping("/locations")
    public void updateLocation(@RequestBody Location location) {
        locationService.update(location);
    }

    @DeleteMapping("/locations/{id}")
    public void deleteLocation(@PathVariable Integer id) {
        locationService.deleteById(id);
    }
}

