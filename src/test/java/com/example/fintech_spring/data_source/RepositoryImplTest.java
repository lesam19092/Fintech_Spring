package com.example.fintech_spring.data_source;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryImplTest {

    private Repository<String, Integer> repository;

    @BeforeEach
    public void setUp() {
        repository = new RepositoryImpl<>();
    }

    @Test
    public void testSave_ShouldStoreKeyValue() {
        String key = "testKey";
        Integer value = 10;

        repository.save(key, value);

        assertEquals(value, repository.findById(key).get());
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
        String key = "nonExistingKey";

        Optional<Integer> optionalValue = repository.findById(key);
        assertFalse(optionalValue.isPresent());
    }

    @Test
    public void testDeleteById_ExistingKey_ShouldReturnTrue() {
        String key = "deleteKey";
        Integer value = 30;

        repository.save(key, value);

        boolean deleted = repository.deleteById(key);

        assertAll(
                () -> {
                    assertTrue(deleted);
                    assertFalse(repository.findById(key).isPresent());
                }
        );

    }

    @Test
    public void testDeleteById_NonExistingKey_ShouldReturnFalse() {
        String key = "notThere";

        boolean deleted = repository.deleteById(key);
        assertFalse(deleted);
    }

    @Test
    public void testExistsById_ExistingKey_ShouldReturnTrue() {
        String key = "exists";
        Integer value = 40;

        repository.save(key, value);

        assertTrue(repository.existsById(key));
    }

    @Test
    public void testExistsById_NonExistingKey_ShouldReturnFalse() {
        String key = "missing";

        assertFalse(repository.existsById(key));
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

        assertEquals(value2, repository.findById(key).get());
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