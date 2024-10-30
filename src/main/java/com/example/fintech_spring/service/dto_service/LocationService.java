package com.example.fintech_spring.service.dto_service;

import com.example.fintech_spring.dto.Location;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LocationService {
    void save(UUID id, Location location);
    Optional<Location> findById(UUID id);
    boolean deleteById(UUID id);
    boolean existsById(UUID id);
    List<Location> findAll();
    void update(UUID id, Location location);
    int getTotalCount();
}