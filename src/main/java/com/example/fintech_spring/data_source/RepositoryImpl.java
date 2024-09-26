package com.example.fintech_spring.data_source;


import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class RepositoryImpl<K, V> implements Repository<K, V> {

    private final Map<K, V> storage = new ConcurrentHashMap<>();

    @Override
    public void save(K key, V value) {
        storage.put(key, value);
    }

    @Override
    public Optional<V> findById(K key) {
        return Optional.ofNullable(storage.get(key));
    }

    @Override
    public boolean deleteById(K key) {
        return storage.remove(key) != null;

    }

    @Override
    public boolean existsById(K key) {
        return storage.containsKey(key);
    }


    @Override
    public List<V> findAll() {
        return new ArrayList<>(storage.values());
    }


    @Override
    public void update(K k, V v) {
        storage.put(k, v);
    }

    @Override
    public int getTotalCount() {
        return storage.size();
    }

}

