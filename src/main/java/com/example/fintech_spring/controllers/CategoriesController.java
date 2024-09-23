package com.example.fintech_spring.controllers;


import com.example.fintech_spring.data_source.Repository;
import com.example.fintech_spring.dto.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class CategoriesController {


    private final Repository<Integer, Category> categoryRepository;

    @GetMapping("/api/v1/places/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }


    @GetMapping("/api/v1/places/categories/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Integer id) {
        Category category = categoryRepository.findById(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }


    @PostMapping("/api/v1/places/categories")
    public ResponseEntity<Category> createGategory(@RequestBody Category category) {
        categoryRepository.save(category.getId(), category);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/api/v1/places/categories/{id}")
    public ResponseEntity<Boolean> updateCategory(@PathVariable Integer id, @RequestBody Category category) {
        boolean updated = categoryRepository.update(id, category);
        return new ResponseEntity<>(updated, HttpStatus.OK);

    }

    @DeleteMapping(value = "/api/v1/places/categories/{id}")
    public ResponseEntity<Boolean> deleteCategory(@PathVariable Integer id) {
        boolean deleted = categoryRepository.deleteById(id);
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }

}

