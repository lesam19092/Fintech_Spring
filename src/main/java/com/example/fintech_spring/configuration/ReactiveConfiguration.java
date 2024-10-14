package com.example.fintech_spring.configuration;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Data
@Configuration
@ConditionalOnProperty(name = "api.client.reactive.enabled", havingValue = "true")
@ConfigurationProperties(prefix = "app")
public class ReactiveConfiguration {

    private String baseUrlForEvents;
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


