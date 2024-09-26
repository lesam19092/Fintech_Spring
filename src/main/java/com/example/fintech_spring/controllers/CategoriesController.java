package com.example.fintech_spring.controllers;


import com.example.fintech_spring.aspect.LogExecutionTime;
import com.example.fintech_spring.dto.Category;
import com.example.fintech_spring.service.RepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@LogExecutionTime
@RequestMapping("/api/v1/places")
public class CategoriesController {


    private final RepositoryService<Integer, Category> categoryRepositoryService;

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryRepositoryService.findAll();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }


    @GetMapping("/categories/{id}")
    public ResponseEntity<Optional<Category>> getCategoryById(@PathVariable Integer id) {
        Optional<Category> category = categoryRepositoryService.findById(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }


    @PostMapping("/categories")
    public ResponseEntity<Category> createGategory(@RequestBody Category category) {
        categoryRepositoryService.save(category.getId(), category);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<Boolean> updateCategory(@PathVariable Integer id, @RequestBody Category category) {
        categoryRepositoryService.update(id,category);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping(value = "/categories/{id}")
    public ResponseEntity<Boolean> deleteCategory(@PathVariable Integer id) {
        boolean deleted = categoryRepositoryService.deleteById(id);
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }

}

