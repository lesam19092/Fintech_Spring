package com.example.fintech_spring.observer;

public interface Publisher {
    void registerObserver(DataObserver observer);

    void unregisterObserver(DataObserver observer);

    void notifyObservers(String notification);
}
