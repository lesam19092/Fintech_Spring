package com.example.fintech_spring.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Data
@Configuration
@ConfigurationProperties(prefix = "thread")
public class ExecutorConfiguration {


    //TODO НАПИСАТЬ АЛЕКСАНДРУ
    //TODO реафкториг

    private int threadCount;

    @Bean
    ExecutorService executorServiceForFetchingData() {
        return Executors.newFixedThreadPool(threadCount);
    }


    @Bean
    ScheduledExecutorService executorSheduled() {
        return Executors.newScheduledThreadPool(threadCount);
    }
}
