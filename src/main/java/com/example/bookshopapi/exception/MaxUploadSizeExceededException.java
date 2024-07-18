package com.example.bookshopapi.exception;

public class MaxUploadSizeExceededException extends RuntimeExceptionHandling{
    public MaxUploadSizeExceededException(String message) {
        super(message);
    }

    public MaxUploadSizeExceededException(String errorCode, String message, String field) {
        super(errorCode, message, field);
    }
}
