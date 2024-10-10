package com.example.fintech_spring.data_source;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryImplTest {

    private RepositoryImpl<String, Integer> repository;

    @BeforeEach
    void setUp() {
        repository = new RepositoryImpl<>();
    }

    @Test
    void testSave_ShouldStoreKeyValue() {
        String key = "testKey";
        Integer value = 10;
        repository.save(key, value);
        var a = repository.findById(key).get();
        assertEquals(value, a);
    }

    @Test
    public void testFindById_ExistingKey_ShouldReturnOptionalWithValue() {
        String key = "existingKey";
        Integer value = 20;
        repository.save(key, value);
        Optional<Integer> optionalValue = repository.findById(key);
        assertAll(
                () -> {
                    assertTrue(optionalValue.isPresent());
                    assertEquals(value, optionalValue.get());
                }
        );
    }

    @Test
    public void testFindById_NonExistingKey_ShouldReturnOptionalEmpty() {
        String nonExistentKey = "nonExistingKey";
        Optional<Integer> optionalValue = repository.findById(nonExistentKey);
        assertFalse(optionalValue.isPresent());
    }

    @Test
    void testDeleteById_ExistingKey_ShouldReturnTrue() {
        String existingKey = "deleteKey";
        Integer value = 30;
        repository.save(existingKey, value);
        boolean deleted = repository.deleteById(existingKey);
        assertAll(
                () -> {
                    assertTrue(deleted);
                    assertFalse(repository.findById(existingKey).isPresent());
                }
        );
    }

    @Test
    void testDeleteById_NonExistingKey_ShouldReturnFalse() {
        String nonExistingKey = "notThere";
        boolean deleted = repository.deleteById(nonExistingKey);
        assertFalse(deleted);
    }

    @Test
    void testExistsById_ExistingKey_ShouldReturnTrue() {
        String existingKey = "exists";
        Integer value = 40;
        repository.save(existingKey, value);
        boolean exists = repository.existsById(existingKey);
        assertTrue(exists);
    }

    @Test
    void testExistsById_NonExistingKey_ShouldReturnFalse() {
        String nonExistingKey = "missing";
        boolean exists = repository.existsById(nonExistingKey);
        assertFalse(exists);
    }

    @Test
    public void testFindAll_EmptyRepository_ShouldReturnEmptyList() {
        List<Integer> allValues = repository.findAll();
        assertTrue(allValues.isEmpty());
    }

    @Test
    public void testFindAll_WithData_ShouldReturnListOfValues() {
        String key1 = "key1";
        Integer value1 = 50;
        String key2 = "key2";
        Integer value2 = 60;
        repository.save(key1, value1);
        repository.save(key2, value2);
        List<Integer> allValues = repository.findAll();
        assertAll(
                () -> {
                    assertEquals(2, allValues.size());
                    assertTrue(allValues.contains(value1));
                    assertTrue(allValues.contains(value2));
                }
        );
    }

    @Test
    public void testUpdate_ShouldUpdateExistingValue() {
        String key = "updateKey";
        Integer value1 = 70;
        Integer value2 = 80;
        repository.save(key, value1);
        repository.update(key, value2);
        Optional<Integer> updatedValue = repository.findById(key);
        assertAll(
                () -> {
                    assertTrue(updatedValue.isPresent());
                    assertEquals(value2, updatedValue.get());
                }
        );
    }

    @Test
    public void testGetTotalCount_EmptyRepository_ShouldReturnZero() {
        int totalCount = repository.getTotalCount();
        assertEquals(0, totalCount);
    }

    @Test
    public void testGetTotalCount_WithData_ShouldReturnCorrectCount() {
        String key1 = "key3";
        Integer value1 = 90;
        String key2 = "key4";
        Integer value2 = 100;
        repository.save(key1, value1);
        repository.save(key2, value2);
        int totalCount = repository.getTotalCount();
        assertEquals(2, totalCount);

    }


}