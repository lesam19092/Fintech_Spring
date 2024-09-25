package com.example.fintech_spring.controllers;


import com.example.fintech_spring.aspect.LogExecutionTime;
import com.example.fintech_spring.data_source.Repository;
import com.example.fintech_spring.dto.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@LogExecutionTime
@RequestMapping("/api/v1/places")
public class CategoriesController {


    private final Repository<Integer, Category> categoryRepository;

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }


    @GetMapping("/categories/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Integer id) {
        Category category = categoryRepository.findById(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }


    @PostMapping("/categories")
    public ResponseEntity<Category> createGategory(@RequestBody Category category) {
        categoryRepository.save(category.getId(), category);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<Boolean> updateCategory(@PathVariable Integer id, @RequestBody Category category) {
        boolean updated = categoryRepository.update(id, category);
        return new ResponseEntity<>(updated, HttpStatus.OK);

    }

    @DeleteMapping(value = "/categories/{id}")
    public ResponseEntity<Boolean> deleteCategory(@PathVariable Integer id) {
        boolean deleted = categoryRepository.deleteById(id);
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }

}

