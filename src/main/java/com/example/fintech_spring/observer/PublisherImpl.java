package com.example.fintech_spring.observer;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PublisherImpl implements Publisher {

    private final List<DataObserver> observers = new ArrayList<>();


    @Override
    public void registerObserver(DataObserver observer) {
        observers.add(observer);
    }

    @Override
    public void unregisterObserver(DataObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String notification) {
        for (DataObserver observer : observers) {
            observer.notifyInformation(notification);
        }
    }
}
