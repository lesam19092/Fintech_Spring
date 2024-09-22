package com.example.fintech_spring.configuration;

import com.example.fintech_spring.data_source.Repository;
import com.example.fintech_spring.data_source.RepositoryImpl;
import com.example.fintech_spring.dto.Category;
import com.example.fintech_spring.dto.Location;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
@Slf4j
public class RepositoryConfiguration {

    @Bean
    public Repository<Integer, Category> dbCategory() {
        log.info("Initializing dbCategory repository");
        return new RepositoryImpl<>();
    }

    @Bean
    public Repository<UUID, Location> dbLocation() {
        log.info("Initializing dbLocation repository");
        return new RepositoryImpl<>();
    }
}