package com.example.fintech_spring.service;

import com.example.fintech_spring.data_source.Repository;
import com.example.fintech_spring.dto.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private Repository<Integer, Category> categoryRepository;

    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category(1, "slug", "name");
    }


    @Test
    void testSave() {
        categoryService.save(category.getId(), category);
        verify(categoryRepository, times(1)).save(category.getId(), category);
    }

    @Test
    void testFindById_ExistingCategory() throws Exception {
        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
        Category foundCategory = categoryService.findById(category.getId());
        assertEquals(category, foundCategory);
    }

    @Test
    void testDeleteById() {
        Integer id = category.getId();
        when(categoryRepository.deleteById(id)).thenReturn(true);
        boolean isDeleted = categoryService.deleteById(id);
        assertTrue(isDeleted);
        verify(categoryRepository, times(1)).deleteById(id);
    }

    @Test
    void testExistsById() {
        Integer id = category.getId();
        when(categoryRepository.existsById(id)).thenReturn(true);
        boolean exists = categoryService.existsById(id);
        assertTrue(exists);
        verify(categoryRepository, times(1)).existsById(id);
    }

    @Test
    void testFindAll() {
        List<Category> categories = Arrays.asList(
                new Category(1, "slug1", "name1"),
                new Category(2, "slug2", "name2")
        );
        when(categoryRepository.findAll()).thenReturn(categories);
        List<Category> allCategories = categoryService.findAll();
        assertEquals(2, allCategories.size());
        assertEquals(categories, allCategories);
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void testUpdate() {
        Integer id = category.getId();
        Category updatedCategory = new Category(id, "updatedSlug", "updatedName");
        categoryService.update(id, updatedCategory);
        verify(categoryRepository, times(1)).update(id, updatedCategory);
    }

    @Test
    void tesGetTotalCount() {
        when(categoryRepository.getTotalCount()).thenReturn(5);
        int totalCount = categoryService.getTotalCount();
        assertEquals(5, totalCount);
        verify(categoryRepository, times(1)).getTotalCount();
    }
}