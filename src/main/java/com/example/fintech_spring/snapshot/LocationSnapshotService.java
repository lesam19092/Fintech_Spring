package com.example.fintech_spring.snapshot;

import com.example.fintech_spring.dto.Location;

import java.util.List;

public interface LocationSnapshotService {

    void saveSnapshot(Location location);

    List<Location> getSnapshots();
}
