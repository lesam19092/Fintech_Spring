package com.example.fintech_spring.repository;

import com.example.fintech_spring.dto.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Integer> {
}
