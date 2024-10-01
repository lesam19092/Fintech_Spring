package com.example.fintech_spring.service;

import com.example.fintech_spring.data_source.Repository;
import com.example.fintech_spring.dto.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class LocationSeriviceTest {

    @Mock
    private Repository<UUID, Location> locationRepository;

    @InjectMocks
    private LocationSerivice locationSerivice;

    private Location location;
    private UUID id;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();
        location = new Location(id, "name", "description");
    }


    @Test
    void testSave() {
        locationSerivice.save(id, location);
        verify(locationRepository, times(1)).save(id, location);
    }


    @Test
    void testFindById_ExistingLocation() throws Exception {
        when(locationRepository.findById(id)).thenReturn(Optional.of(location));
        Location foundLocation = locationSerivice.findById(id);
        assertEquals(location, foundLocation);
    }

    @Test
    void testDeleteById() {
        when(locationRepository.deleteById(id)).thenReturn(true);
        boolean isDeleted = locationSerivice.deleteById(id);
        assertTrue(isDeleted);
        verify(locationRepository, times(1)).deleteById(id);
    }

    @Test
    void testExistsById() {
        when(locationRepository.existsById(id)).thenReturn(true);
        boolean exists = locationSerivice.existsById(id);
        assertTrue(exists);
        verify(locationRepository, times(1)).existsById(id);
    }

    @Test
    void testFindAll() {
        List<Location> locations = Arrays.asList(
                new Location(UUID.randomUUID(), "name1", "description1"),
                new Location(UUID.randomUUID(), "name2", "description2")
        );
        when(locationRepository.findAll()).thenReturn(locations);
        List<Location> allLocations = locationSerivice.findAll();
        assertEquals(2, allLocations.size());
        assertEquals(locations, allLocations);
        verify(locationRepository, times(1)).findAll();
    }

    @Test
    void testUpdate() {
        Location updatedLocation = new Location(id, "updatedName", "updatedDescription");
        locationSerivice.update(id, updatedLocation);
        verify(locationRepository, times(1)).update(id, updatedLocation);
    }

    @Test
    void testGetTotalCount() {
        when(locationRepository.getTotalCount()).thenReturn(5);
        int totalCount = locationSerivice.getTotalCount();
        assertEquals(5, totalCount);
        verify(locationRepository, times(1)).getTotalCount();
    }
}