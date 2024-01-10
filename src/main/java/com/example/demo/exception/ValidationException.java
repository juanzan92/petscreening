package com.example.demo.exception;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }

    public static ValidationException getException(String message) {
        throw new ValidationException(String.format("Validation Exception error: %s", message));
    }
}
