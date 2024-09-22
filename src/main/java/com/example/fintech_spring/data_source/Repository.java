package com.example.fintech_spring.data_source;


import java.util.List;

public interface Repository<K, V> {

    void save(K key, V value);

    V get(K key);

    boolean remove(K key);

    boolean contains(K key);

    List<V> getAllValues();

    void soutData(); //TODO ( КАК БУДУ ЗАЛИВАТЬ УБРАТЬ)

    boolean update(K k, V v);

    int getTotalCount();
}
