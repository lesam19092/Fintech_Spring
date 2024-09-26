package com.example.fintech_spring.data_source;


import java.util.List;
import java.util.Optional;

public interface Repository<K, V> {

    void save(K key, V value);

    Optional<V> findById(K key);

    boolean deleteById(K key);

    boolean existsById(K key);

    List<V> findAll();

    void update(K k, V v);

    int getTotalCount();
}
