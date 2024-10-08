package com.example.hw8.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestClient;

@Configuration
public class RestTemplateConfiguration {


    @Value("${app.baseUrl}")
    private String baseUrl;


    @Bean
    public RestClient bankRestClient(HttpMessageConverter<Object> createXmlHttpMessageConverter) {
        return RestClient
                .builder()
                .baseUrl(baseUrl)
                .messageConverters(
                        httpMessageConverters ->
                                httpMessageConverters.add(createXmlHttpMessageConverter))
                .build();
    }
}
