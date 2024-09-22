package com.example.fintech_spring.data_source;


import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class SimpleStorageImpl<K, V> implements SimpleStorage<K, V> {

    private final Map<K, V> storage = new ConcurrentHashMap<>();

    @Override
    public void put(K key, V value) {
        storage.put(key, value);
    }

    @Override
    public V get(K key) {
        return storage.get(key);
    }

    @Override
    public boolean remove(K key) {
        return storage.remove(key) != null;

    }

    @Override
    public boolean containsKey(K key) {
        return storage.containsKey(key);
    }

    @Override
    public Map<K, V> getStorage() {
        return storage;
    }

    @Override
    public List<V> getList() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void soutData() {
        storage.forEach((k, v) -> {
            System.out.printf("Key: %s, Value: %s%n", k, v);
        });
    }

    @Override
    public boolean update(K k, V v) {
        if (storage.containsKey(k)) {
            storage.put(k, v);
            return true;
        } else {
            storage.put(k, v);
            return true;
        }
    }

}

