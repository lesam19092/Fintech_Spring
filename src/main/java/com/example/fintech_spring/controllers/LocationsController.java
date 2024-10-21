package com.example.fintech_spring.controllers;


import com.example.fintech_spring.dto.LocationDto;
import com.example.fintech_spring.service.location.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class LocationsController {

    private final LocationService locationService;


    @GetMapping("/locations/{id}")
    public LocationDto getLocationById(@PathVariable Integer id) {
        return locationService.findById(id);
    }


    @PostMapping("/locations")
    public void createLocation(@RequestBody LocationDto locationDto) {
        locationService.save(locationDto);
    }


    @PutMapping("/locations/{id}")
    public void updateLocation(@PathVariable Integer id, @RequestBody LocationDto locationDto) {
        locationService.update(id, locationDto);
    }

    @DeleteMapping("/locations/{id}")
    public void deleteLocation(@PathVariable Integer id) {
        locationService.deleteById(id);
    }
}

