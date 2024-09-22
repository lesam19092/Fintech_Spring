package com.example.fintech_spring.configuration;

import com.example.fintech_spring.data_source.SimpleStorage;
import com.example.fintech_spring.data_source.SimpleStorageImpl;
import com.example.fintech_spring.dto.Category;
import com.example.fintech_spring.dto.Location;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class StorageConfiguration {

    @Bean
    public SimpleStorage<Integer, Category> dbCategory() {
        return new SimpleStorageImpl<>();
    }

    @Bean
    public SimpleStorage<UUID, Location> dbLocation() {
        return new SimpleStorageImpl<>();
    }
}
