package com.example.fintech_spring.data_source;


import java.util.List;

public interface Repository<K, V> {

    void save(K key, V value);

    V findById(K key);

    boolean deleteById(K key);

    boolean existsById(K key);

    List<V> findAll();

    void soutData(); //TODO ( КАК БУДУ ЗАЛИВАТЬ УБРАТЬ)

    boolean update(K k, V v);

    int getTotalCount();
}
