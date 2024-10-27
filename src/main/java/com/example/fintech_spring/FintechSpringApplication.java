package com.example.fintech_spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example.fintech_spring", "com.example.loggingstarter"})
public class FintechSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(FintechSpringApplication.class, args);
    }

}
