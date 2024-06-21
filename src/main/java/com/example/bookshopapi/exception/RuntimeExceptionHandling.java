package com.example.bookshopapi.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RuntimeExceptionHandling extends RuntimeException {

    private String errorCode;
    private String field;
    private StackTraceElement traceElement;


    public RuntimeExceptionHandling(String message) {
        super(message);
    }

    public RuntimeExceptionHandling(String errorCode,
                                    String field,
                                    String message) {
        super(message);
        this.errorCode = errorCode;
        this.field = field;
    }

}