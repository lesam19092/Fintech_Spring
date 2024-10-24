package com.example.fintech_spring.komanda;

import com.example.fintech_spring.data_source.Repository;
import com.example.fintech_spring.dto.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class InsertLocationCommand implements Command<Location> {

    private final Repository<UUID, Location> locationRepository;


    @Override
    public void execute(Location entity) {
        UUID id = UUID.randomUUID();
        entity.setUuid(id);
        locationRepository.save(id, entity);
    }
}
