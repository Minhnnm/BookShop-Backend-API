package com.example.bookshopapi.exception;

public class NotFoundException extends RuntimeExceptionHandling{
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String errorCode, String message, String field) {
        super(errorCode, message, field);
    }
}
