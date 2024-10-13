package com.example.fintech_spring.service.controlling;

import com.example.fintech_spring.dto.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    void save(Integer id, Category category);

    Optional<Category> findById(Integer id);

    boolean deleteById(Integer id);

    boolean existsById(Integer id);

    List<Category> findAll();

    void update(Integer id, Category category);

    int getTotalCount();
}