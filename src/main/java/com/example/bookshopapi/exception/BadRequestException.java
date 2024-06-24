package com.example.bookshopapi.exception;

public class BadRequestException extends RuntimeExceptionHandling{
    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String errorCode, String field, String message) {
        super(errorCode, field, message);
    }
}
