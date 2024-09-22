package com.example.fintech_spring.controllers;

//TODO(REFACTOR)

import com.example.fintech_spring.data_source.Repository;
import com.example.fintech_spring.dto.Category;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class CategoriesController {


    private final Repository<Integer, Category> dbCategory;


    public CategoriesController(Repository<Integer, Category> dbCategory) {
        this.dbCategory = dbCategory;
    }

    @GetMapping(value = "/api/v1/places/categories")
    public ResponseEntity<List<Category>> getAllCategories() {

        List<Category> categories = dbCategory.findAll();
        return categories != null && !categories.isEmpty()
                ? new ResponseEntity<>(categories, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }


    @GetMapping(value = "/api/v1/places/categories/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable(name = "id") Integer id) {
        Category category = dbCategory.findById(id);
        return category != null
                ? new ResponseEntity<>(category, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);


    }


    @PostMapping(value = "/api/v1/places/categories")
    public ResponseEntity<Category> createGategory(@RequestBody Category category) {
        dbCategory.save(category.getId(), category);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/api/v1/places/categories/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable(name = "id") Integer id, @RequestBody Category category) {

        boolean updated = dbCategory.update(id, category);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/api/v1/places/categories/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable(name = "id") Integer id) {


        boolean deleted = dbCategory.deleteById(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }


}

