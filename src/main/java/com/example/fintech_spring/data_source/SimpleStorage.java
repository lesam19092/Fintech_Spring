package com.example.fintech_spring.data_source;


import java.util.List;
import java.util.Map;

public interface SimpleStorage<K, V> {

    void put(K key, V value);

    V get(K key);

    boolean remove(K key);

    boolean containsKey(K key);

    Map<K, V> getStorage();

    List<V> getList();

    void soutData();

    boolean update(K k, V v);
}
