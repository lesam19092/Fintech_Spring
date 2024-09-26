package com.example.fintech_spring.service;

import com.example.fintech_spring.data_source.Repository;
import com.example.fintech_spring.data_source.RepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RepositoryService<K, V> implements Repository<K, V> {

    private final RepositoryImpl<K, V> repository;

    @Override
    public void save(K key, V value) {
        repository.save(key, value);
    }

    @Override
    public Optional<V> findById(K key) {
        return repository.findById(key);
    }

    @Override
    public boolean deleteById(K key) {
        return repository.deleteById(key);
    }

    @Override
    public boolean existsById(K key) {
        return repository.existsById(key);
    }

    @Override
    public List<V> findAll() {
        return repository.findAll();
    }

    @Override
    public void update(K k, V v) {
        repository.update(k, v);
    }

    @Override
    public int getTotalCount() {
        return repository.getTotalCount();
    }
}
