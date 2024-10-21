package com.example.fintech_spring.service.location;

import com.example.fintech_spring.dto.LocationDto;
import com.example.fintech_spring.dto.entity.Location;

import java.util.List;

public interface LocationService {
    void save(LocationDto locationDto);

    LocationDto findById(Integer id);

    void deleteById(Integer id);

    void update(Integer id, LocationDto locationDto);

    void deleteAll();

    List<Location> findAll();

}