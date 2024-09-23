package com.example.fintech_spring.exception;

import lombok.*;
import org.springframework.http.HttpStatus;


@AllArgsConstructor
@NoArgsConstructor
@Data
class ApiError {
    private HttpStatus status;
    private String message;
}
