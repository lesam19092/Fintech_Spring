package com.example.fintech_spring.repository;

import com.example.fintech_spring.dto.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {
}
