package com.example.fintech_spring.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ReactiveConfiguration {

    @Value("${app.baseUrl}")
    private String baseUrlForEvents;

    @Value("${app.baseUrlForConvertValute}")
    private String baseUrlForConvertValute;

    @Bean
    public WebClient webClientForEvents() {
        return WebClient.builder()
                .baseUrl(baseUrlForEvents)
                .build();
    }

    @Bean
    public WebClient webClientForConvertValute() {
        return WebClient.builder()
                .baseUrl(baseUrlForConvertValute)
                .build();
    }

}


