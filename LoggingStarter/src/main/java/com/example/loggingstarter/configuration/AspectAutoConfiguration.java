package com.example.loggingstarter.configuration;

import com.example.loggingstarter.annotation.LogExecutionTimeAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AspectAutoConfiguration {

    @Bean
    public LogExecutionTimeAspect enableTimeMeasurementLoggingAspect() {
        return new LogExecutionTimeAspect();
    }
}