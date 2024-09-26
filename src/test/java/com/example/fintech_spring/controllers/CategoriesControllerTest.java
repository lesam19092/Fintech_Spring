package com.example.fintech_spring.controllers;

import com.example.fintech_spring.data_source.Repository;
import com.example.fintech_spring.dto.Category;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {CategoriesController.class})
class CategoriesControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private Repository<Integer, Category> categoryRepository;
    private static ObjectMapper mapper = new ObjectMapper();


    @Test
    void getAllCategories() throws Exception {
        List<Category> categories = new ArrayList<>();

        categories.add(new Category(1, "slug1", "name1"));
        categories.add(new Category(2, "slug2", "name2"));

        when(categoryRepository.findAll()).thenReturn(categories);
        mockMvc.perform(get("/api/v1/places/categories"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].name", Matchers.equalTo("name1")));
    }

    @Test
    void getCategoryById() throws Exception {


        Category category = new Category(1, "slug1", "name1");

        String expectedJson = mapper
                .writeValueAsString(new Category(1, "slug1", "name1"));

        when(categoryRepository.findById(1)).thenReturn(Optional.of(category));

        mockMvc.perform(get("/api/v1/places/categories/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));


    }

    @Test
    void createGategory() throws Exception {
        String expectedJson = mapper.writeValueAsString(new Category(1, "slug1", "name1"));
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/places/categories")
                        .content(expectedJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void updateCategory() throws Exception {
        String expectedJson = mapper.writeValueAsString(new Category(1, "slug1", "name1"));
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/places/categories/{id}", 1)
                        .content(expectedJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteCategory() throws Exception {

        when(categoryRepository.deleteById(1)).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/places/categories/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


    }
}