package com.example.fintech_spring.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Configuration
public class ExecutorConfiguration {

    //TODO ConfiguraionOnProperties

    @Value("${thread.count}")
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
