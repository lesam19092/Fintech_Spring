package com.example.fintech_spring.service.location;

import com.example.fintech_spring.dto.LocationDto;

public interface LocationService {
    void save(LocationDto locationDto);

    LocationDto findById(Integer id);

    void deleteById(Integer id);

    void update(Integer id, LocationDto locationDto);

}