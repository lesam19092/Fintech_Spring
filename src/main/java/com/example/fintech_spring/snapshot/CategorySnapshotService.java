package com.example.fintech_spring.snapshot;

import com.example.fintech_spring.dto.Category;

import java.util.List;

public interface CategorySnapshotService {


    void saveSnapshot(Integer id, Category category);

    List<Category> getSnapshots();
}
