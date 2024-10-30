package com.example.hw8.exception;

public class CurrencyExistsButNotFoundException extends RuntimeException {
    public CurrencyExistsButNotFoundException(String message) {
        super(message);
    }
}
