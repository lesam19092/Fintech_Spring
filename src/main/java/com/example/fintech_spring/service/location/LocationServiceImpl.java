package com.example.fintech_spring.service.location;

import com.example.fintech_spring.dto.LocationDto;
import com.example.fintech_spring.dto.entity.Event;
import com.example.fintech_spring.dto.entity.Location;
import com.example.fintech_spring.repository.LocationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {


    private final LocationRepository locationRepository;


    public void save(LocationDto locationDto) {
        locationRepository.save(getLocationFromDto(locationDto));
    }

    @Override
    @Transactional(readOnly = true)
    public LocationDto findById(Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Location with id " + id + " not found"));
        return convertToDto(location);

    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        locationRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(Long id, LocationDto locationDto) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Location with id " + id + " not found"));
        updateLocationFromDto(location, locationDto);
        locationRepository.save(location);

    }

    @Override
    @Transactional
    public void deleteAll() {
        locationRepository.deleteAll();
    }

    @Transactional(readOnly = true)
    public List<Location> findAll() {
        return locationRepository.findAll();
    }


    private LocationDto convertToDto(Location location) {
        return new LocationDto(
                location.getName(),
                location.getSlug(),
                location.getEvents().stream().map(Event::getId).toList());
    }

    private Location getLocationFromDto(LocationDto locationDto) {
        Location location = new Location();
        location.setSlug(locationDto.getSlug());
        location.setName(locationDto.getName());
        return location;
    }

    private void updateLocationFromDto(Location location, LocationDto locationDto) {
        location.setName(locationDto.getName());
        location.setSlug(locationDto.getSlug());
    }


}
