package com.example.fintech_spring.service.location;

import com.example.fintech_spring.dto.Location;

import java.util.List;
import java.util.Optional;

public interface LocationService {
    void save(Location location);

    Location findById(Integer id);

    void deleteById(Integer id);

    void update(Location location);

}