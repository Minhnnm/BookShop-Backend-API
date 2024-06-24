package com.example.bookshopapi.exception;

public class UnAuthorizedException extends RuntimeExceptionHandling{
    public UnAuthorizedException(String message) {
        super(message);
    }

    public UnAuthorizedException(String errorCode, String field, String message) {
        super(errorCode, field, message);
    }
}
