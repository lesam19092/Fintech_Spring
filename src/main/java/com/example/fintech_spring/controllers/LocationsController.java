package com.example.fintech_spring.controllers;

//TODO(REFACTOR)

import com.example.fintech_spring.data_source.Repository;
import com.example.fintech_spring.dto.Location;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
public class LocationsController {

    private final Repository<UUID, Location> dbLocation;


    public LocationsController(Repository<UUID, Location> dbLocation) {
        this.dbLocation = dbLocation;
    }

    @GetMapping(value = "/api/v1/locations")
    public ResponseEntity<List<Location>> getAllLocations() {

        List<Location> locations = dbLocation.findAll();
        return locations != null && !locations.isEmpty()
                ? new ResponseEntity<>(locations, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }


    @GetMapping(value = "/api/v1/locations/{id}")
    public ResponseEntity<Location> getCategoryById(@PathVariable(name = "id") UUID id) {
        Location location = dbLocation.findById(id);
        return location != null
                ? new ResponseEntity<>(location, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);


    }


    @PostMapping(value = "/api/v1/locations")
    public ResponseEntity<Location> createGategory(@RequestBody Location location) {
        dbLocation.save(location.getUuid(), location);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PutMapping(value = "/api/v1/locations/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable(name = "id") UUID id, @RequestBody Location location) {

        boolean updated = dbLocation.update(id, location);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/api/v1/locations/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable(name = "id") UUID id) {


        boolean deleted = dbLocation.deleteById(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }


}

