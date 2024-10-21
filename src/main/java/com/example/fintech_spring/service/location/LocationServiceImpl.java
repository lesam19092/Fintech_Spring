package com.example.fintech_spring.service.location;

import com.example.fintech_spring.dto.LocationDto;
import com.example.fintech_spring.dto.entity.Event;
import com.example.fintech_spring.dto.entity.Location;
import com.example.fintech_spring.repository.LocationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {


    private final LocationRepository locationRepository;


    public void save(LocationDto locationDto) {
        locationRepository.save(getLocationFromDto(locationDto));
    }

    @Override
    public LocationDto findById(Integer id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Location with id " + id + " not found"));
        return convertToDto(location);

    }

    @Override
    public void deleteById(Integer id) {
        locationRepository.deleteById(id);
    }

    @Override
    public void update(Integer id, LocationDto locationDto) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Location with id " + id + " not found"));
        updateLocationFromDto(location, locationDto);
        locationRepository.save(location);

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
