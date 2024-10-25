package com.example.fintech_spring.snapshot;


import com.example.fintech_spring.dto.Location;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class LocationSnapshotServiceImpl implements LocationSnapshotService {
    private final List<Location> snapshots = new ArrayList<>();

    public void saveSnapshot(Location location) {
        snapshots.add(new Location(location.getUuid(), location.getSlug(), location.getName()));
    }

    public List<Location> getSnapshots() {
        return new ArrayList<>(snapshots);
    }
}