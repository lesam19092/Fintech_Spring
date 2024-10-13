package com.example.fintech_spring.service;

import com.example.fintech_spring.data_source.Repository;
import com.example.fintech_spring.dto.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class LocationSerivice {


    private final Repository<UUID, Location> locationRepository;

    public void save(UUID id, Location location) {
        locationRepository.save(id, location);
    }

    public Location findById(UUID id) throws HttpRequestMethodNotSupportedException {

        if (locationRepository.findById(id).isEmpty()) {
            throw new HttpRequestMethodNotSupportedException("локации нет в базе");
        }
        return locationRepository.findById(id).get();
    }

    public boolean deleteById(UUID id) {
        return locationRepository.deleteById(id);
    }

    public boolean existsById(UUID id) {
        return locationRepository.existsById(id);
    }

    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    public void update(UUID id, Location location) {
        locationRepository.update(id, location);
    }

    public int getTotalCount() {
        return locationRepository.getTotalCount();
    }


}
