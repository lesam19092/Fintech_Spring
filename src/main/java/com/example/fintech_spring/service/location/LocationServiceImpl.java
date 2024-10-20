package com.example.fintech_spring.service.location;

import com.example.fintech_spring.dto.Location;
import com.example.fintech_spring.repository.LocationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {


    private final LocationRepository locationRepository;

    public void save(Location location) {
        locationRepository.save(location);
    }

    @Override
    public Location findById(Integer id) {
        return locationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Location with id " + id + " not found"));
    }

    @Override
    public void deleteById(Integer id) {
        locationRepository.deleteById(id);
    }

    @Override
    public void update(Location location) {
        Optional<Location> existingLocation = locationRepository.findById(location.getId());
        if (existingLocation.isPresent()) {
            locationRepository.save(location);
        } else {
            throw new EntityNotFoundException("Location with id " + location.getId() + " not found");
        }
    }


}
