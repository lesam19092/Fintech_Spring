package com.example.hw8;

import com.example.hw8.configuration.RestTemplateConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RestTemplateConfiguration.class)
public class Hw8Application {

    public static void main(String[] args) {

        SpringApplication.run(Hw8Application.class, args);
    }

}
