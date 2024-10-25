package com.example.fintech_spring.observer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DataSavingObserver implements DataObserver {

    @Override
    public void notifyInformation(String data) {
        log.info("Data has been saved: " + data);
    }
}
