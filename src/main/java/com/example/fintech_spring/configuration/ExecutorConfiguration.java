package com.example.fintech_spring.configuration;

import lombok.Data;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
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

    private int threadCount;

    @Bean
    ExecutorService executorServiceForFetchingData() {
        BasicThreadFactory factory = new BasicThreadFactory.Builder()
                .namingPattern("MyCustomThreadForFetchingData-%d").priority(Thread.MAX_PRIORITY).build();
        return Executors.newFixedThreadPool(threadCount, factory);
    }


    @Bean
    ScheduledExecutorService executorSheduled() {
        BasicThreadFactory factory = new BasicThreadFactory.Builder()
                .namingPattern("MyCustomThreadForScheduled-%d").priority(Thread.MAX_PRIORITY).build();
        return Executors.newScheduledThreadPool(threadCount, factory);
    }
}
