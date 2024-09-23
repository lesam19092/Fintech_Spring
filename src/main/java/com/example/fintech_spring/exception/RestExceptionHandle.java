package com.example.fintech_spring.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.Arrays;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RestExceptionHandle {


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handle(HttpRequestMethodNotSupportedException ex) {
        String message = String.format("Unsupported request method: %s. Supported methods: %s",
                ex.getMethod(), Arrays.toString(ex.getSupportedMethods()));
        return new ApiError(HttpStatus.BAD_REQUEST, String.format("Сообщение: %s\nОшибка: %s%n", message, ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handle(MethodArgumentTypeMismatchException ex) {
        String message = String.format("Expected argument of type %s, but received %s for parameter '%s'",
                ex.getRequiredType().getSimpleName(), ex.getValue() == null ? "null" : ex.getValue().getClass().getSimpleName(), ex.getParameter());
        return new ApiError(HttpStatus.BAD_REQUEST, String.format("Сообщение: %s\nОшибка: %s%n", message, ex.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handle(HttpMessageNotReadableException ex) {
        String message = "Failed to read request body. Please ensure the request body is formatted correctly.";
        return new ApiError(HttpStatus.BAD_REQUEST, String.format("Сообщение: %s\nОшибка: %s%n", message, ex.getMessage()));
    }


}