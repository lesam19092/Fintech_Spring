package com.example.fintech_spring.repository;

import com.example.fintech_spring.dto.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Integer> {


    @Query("SELECT l FROM Location l JOIN FETCH l.events WHERE l.id = :id")
    Optional<Location> findByIdWithEvents(Integer id);
}
