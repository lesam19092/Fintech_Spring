package com.example.fintech_spring.service.location;

import com.example.fintech_spring.data_source.Repository;
import com.example.fintech_spring.dto.Location;
import com.example.fintech_spring.snapshot.LocationSnapshotService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class LocationSeriviceImpl implements LocationService {


    private final Repository<UUID, Location> locationRepository;
    private final LocationSnapshotService locationSnapshotService;

    @Override
    public void save(UUID id, Location location) {
        locationSnapshotService.saveSnapshot(location);
        locationRepository.save(id, location);
    }

    @Override
    public Location findById(UUID id) {
        Optional<Location> location = locationRepository.findById(id);
        if (location.isPresent()) {
            return location.get();
        } else {
            throw new EntityNotFoundException("Location not found");
        }
    }

    @Override
    public boolean deleteById(UUID id) {
        Optional<Location> location = locationRepository.findById(id);
        if (location.isPresent()) {
            locationSnapshotService.saveSnapshot(location.get());
            return locationRepository.deleteById(id);
        }
        return false;
    }

    @Override
    public boolean existsById(UUID id) {
        return locationRepository.existsById(id);
    }

    @Override
    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    @Override
    public void update(UUID id, Location location) {
        locationSnapshotService.saveSnapshot(location);
        locationRepository.update(id, location);
    }

    @Override
    public int getTotalCount() {
        return locationRepository.getTotalCount();
    }


}
