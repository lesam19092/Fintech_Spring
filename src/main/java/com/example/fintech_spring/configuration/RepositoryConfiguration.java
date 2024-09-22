package com.example.fintech_spring.configuration;

import com.example.fintech_spring.data_source.Repository;
import com.example.fintech_spring.data_source.RepositoryImpl;
import com.example.fintech_spring.dto.Category;
import com.example.fintech_spring.dto.Location;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class RepositoryConfiguration {

    @Bean
    public Repository<Integer, Category> dbCategory() {
        return new RepositoryImpl<>();
    }

    @Bean
    public Repository<UUID, Location> dbLocation() {
        return new RepositoryImpl<>();
    }
}
