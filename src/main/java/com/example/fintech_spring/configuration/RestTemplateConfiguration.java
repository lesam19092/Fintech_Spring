package com.example.fintech_spring.configuration;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class RestTemplateConfiguration {

    //TODO ConfiguraionOnProperties

    @Value("${app.baseUrl}")
    private String baseUrlForEvents;

    @Value("${app.baseUrlForConvertValute}")
    private String baseUrlForConvertValute;

    @Bean
    public RestTemplate restTemplateForEvents() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(baseUrlForEvents));
        return restTemplate;
    }

    @Bean
    public RestTemplate restTemplateForConvertValute() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(baseUrlForConvertValute));
        return restTemplate;
    }
}
