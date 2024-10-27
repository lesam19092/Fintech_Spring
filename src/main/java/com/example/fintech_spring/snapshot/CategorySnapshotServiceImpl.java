package com.example.fintech_spring.snapshot;

import com.example.fintech_spring.dto.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategorySnapshotServiceImpl implements CategorySnapshotService {
    private final List<Category> snapshots = new ArrayList<>();

    public void saveSnapshot(Integer id, Category category) {
        snapshots.add(new Category(id, category.getSlug(), category.getName()));
    }

    public List<Category> getSnapshots() {
        return new ArrayList<>(snapshots);
    }
}