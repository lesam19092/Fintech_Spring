package com.example.fintech_spring.komanda;


public interface Command<T> {
    void execute(T entity);
}