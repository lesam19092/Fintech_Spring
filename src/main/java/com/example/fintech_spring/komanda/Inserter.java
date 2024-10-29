package com.example.fintech_spring.komanda;

import com.example.fintech_spring.dto.Category;
import com.example.fintech_spring.dto.Location;

public interface Inserter {
    void insertLocation(Location location);

    void insertCategory(Category category);
}
