package com.example.bookshopapi.exception.handler;

import com.example.bookshopapi.exception.ExistedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ExistedException.class)
    public ResponseEntity<GlobalExceptionError> handleExistedException(ExistedException e) throws Exception {
        GlobalExceptionError apiError = new GlobalExceptionError(HttpStatus.BAD_REQUEST, e.getErrorCode(),  e.getMessage(),
                LocalDateTime.now(), e.getField());
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
