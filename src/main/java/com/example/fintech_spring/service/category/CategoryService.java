package com.example.fintech_spring.service.category;

import com.example.fintech_spring.dto.Category;

import java.util.List;

public interface CategoryService {

    void save(Integer id, Category category);

    Category findById(Integer id);

    boolean deleteById(Integer id);

    boolean existsById(Integer id);

    List<Category> findAll();

    void update(Integer id, Category category);

    int getTotalCount();
}
