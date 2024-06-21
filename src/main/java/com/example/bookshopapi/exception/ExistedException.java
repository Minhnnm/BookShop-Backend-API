package com.example.bookshopapi.exception;

public class ExistedException extends RuntimeExceptionHandling{
    public ExistedException(String message) {
        super(message);
    }
    public ExistedException(String errorCode, String message,  String field){
        super(errorCode, message, field);
    }
}
