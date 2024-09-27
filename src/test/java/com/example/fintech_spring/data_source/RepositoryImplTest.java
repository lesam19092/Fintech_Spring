package com.example.fintech_spring.data_source;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RepositoryImplTest {

    @Mock
    private RepositoryImpl<String, Integer> repository;


    @Test
    void testSave_ShouldStoreKeyValue() {
        String key = "testKey";
        Integer value = 10;
        when(repository.findById(key)).thenReturn(Optional.of(value));
        var a = repository.findById(key).get();
        assertEquals(value, a);
    }

    @Test
    public void testFindById_ExistingKey_ShouldReturnOptionalWithValue() {
        String key = "existingKey";
        Integer value = 20;
        when(repository.findById(key)).thenReturn(Optional.of(value));
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
        when(repository.findById(nonExistentKey)).thenReturn(Optional.empty());
        Optional<Integer> optionalValue = repository.findById(nonExistentKey);
        assertFalse(optionalValue.isPresent());
    }

    @Test
    void testDeleteById_ExistingKey_ShouldReturnTrue() {
        String existingKey = "deleteKey";
        Integer value = 30;

        when(repository.deleteById(existingKey)).thenReturn(true);

        boolean deleted = repository.deleteById(existingKey);

        assertAll(
                () -> {
                    assertTrue(deleted);
                    verify(repository).deleteById(existingKey);
                    assertFalse(repository.findById(existingKey).isPresent());
                }
        );
    }

    @Test
    void testDeleteById_NonExistingKey_ShouldReturnFalse() {
        String nonExistingKey = "notThere";

        when(repository.deleteById(nonExistingKey)).thenReturn(false);

        boolean deleted = repository.deleteById(nonExistingKey);

        assertAll(
                () -> {
                    assertFalse(deleted);
                    verify(repository).deleteById(nonExistingKey);

                }
        );

    }

    @Test
    void testExistsById_ExistingKey_ShouldReturnTrue() {
        String existingKey = "exists";
        Integer value = 40;
        when(repository.existsById(existingKey)).thenReturn(true);
        boolean exists = repository.existsById(existingKey);

        assertAll(
                () -> {
                    assertTrue(exists);
                    verify(repository).existsById(existingKey);

                }
        );
    }

    @Test
    void testExistsById_NonExistingKey_ShouldReturnFalse() {
        String nonExistingKey = "missing";
        when(repository.existsById(nonExistingKey)).thenReturn(false);
        boolean exists = repository.existsById(nonExistingKey);

        assertAll(
                () -> {
                    assertFalse(exists);
                    verify(repository).existsById(nonExistingKey);

                }
        );
    }

    @Test
    public void testFindAll_EmptyRepository_ShouldReturnEmptyList() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        List<Integer> allValues = repository.findAll();

        assertAll(
                () -> {
                    assertTrue(allValues.isEmpty());
                    verify(repository).findAll();

                }
        );
    }

    @Test
    public void testFindAll_WithData_ShouldReturnListOfValues() {
        String key1 = "key1";
        Integer value1 = 50;
        String key2 = "key2";
        Integer value2 = 60;
        when(repository.findAll()).thenReturn(Arrays.asList(value1, value2));
        repository.save(key1, value1);
        repository.save(key2, value2);
        List<Integer> allValues = repository.findAll();
        assertAll(
                () -> {
                    assertEquals(2, allValues.size());
                    assertTrue(allValues.contains(value1));
                    assertTrue(allValues.contains(value2));
                    verify(repository, times(1)).findAll();

                }
        );
    }

    @Test
    public void testUpdate_ShouldUpdateExistingValue() {
        String key = "updateKey";
        Integer value1 = 70;
        Integer value2 = 80;

        when(repository.findById(key)).thenReturn(Optional.of(value2));

        repository.save(key, value1);
        repository.update(key, value2);
        Optional<Integer> updatedValue = repository.findById(key);

        assertAll(
                () -> {
                    assertTrue(updatedValue.isPresent());
                    assertEquals(value2, updatedValue.get());
                    verify(repository, times(1)).findById(key);
                    verify(repository, times(1)).update(key, value2);
                }
        );
    }

    @Test
    public void testGetTotalCount_EmptyRepository_ShouldReturnZero() {
        when(repository.getTotalCount()).thenReturn(0);
        int totalCount = repository.getTotalCount();

        assertAll(
                () -> {
                    assertEquals(0, totalCount);
                    verify(repository, times(1)).getTotalCount();
                }
        );
    }

    @Test
    public void testGetTotalCount_WithData_ShouldReturnCorrectCount() {
        String key1 = "key3";
        Integer value1 = 90;
        String key2 = "key4";
        Integer value2 = 100;

        when(repository.getTotalCount()).thenReturn(2);

        repository.save(key1, value1);
        repository.save(key2, value2);
        int totalCount = repository.getTotalCount();

        assertAll(
                () -> {
                    assertEquals(2, totalCount);
                    verify(repository, times(1)).getTotalCount();
                }
        );

    }
}