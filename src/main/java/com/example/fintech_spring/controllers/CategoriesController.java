package com.example.fintech_spring.controllers;


import com.example.fintech_spring.dto.Category;
import com.example.fintech_spring.service.dto_service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/places")
public class CategoriesController {


    private final CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.findAll();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }


    @GetMapping("/categories/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Integer id) throws HttpRequestMethodNotSupportedException {
        Category category = categoryService.findById(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }


    @PostMapping("/categories")
    public ResponseEntity<Category> createGategory(@RequestBody Category category) {
        categoryService.save(category.getId(), category);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<Boolean> updateCategory(@PathVariable Integer id, @RequestBody Category category) {
        categoryService.update(id, category);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping(value = "/categories/{id}")
    public ResponseEntity<Boolean> deleteCategory(@PathVariable Integer id) {
        boolean deleted = categoryService.deleteById(id);
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }

}

