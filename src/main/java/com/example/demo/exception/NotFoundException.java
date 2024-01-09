package com.example.demo.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }

    public static final String PREFIX = "Object not found for key %s ";

    public static NotFoundException getException(String id) {
        return new NotFoundException(String.format(PREFIX, id));
    }
}
