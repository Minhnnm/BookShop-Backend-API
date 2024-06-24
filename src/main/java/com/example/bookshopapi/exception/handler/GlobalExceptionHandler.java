package com.example.bookshopapi.exception.handler;

import com.example.bookshopapi.exception.BadRequestException;
import com.example.bookshopapi.exception.ExistedException;
import com.example.bookshopapi.exception.UnAuthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ExistedException.class)
    public ResponseEntity<GlobalExceptionError> handleExistedException(ExistedException e) throws Exception {
        GlobalExceptionError apiError = new GlobalExceptionError(HttpStatus.BAD_REQUEST, e.getErrorCode(), e.getField(),
                LocalDateTime.now(), e.getMessage());
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<GlobalExceptionError> handleBadRequestException(BadRequestException e) {
        GlobalExceptionError apiError = GlobalExceptionError.builder()
                .errorCode(e.getErrorCode())
                .timestamp(LocalDateTime.now())
                .field(e.getField())
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .field(e.getField()).build();
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<GlobalExceptionError> handeUnAuthorizedException(UnAuthorizedException e) {
        GlobalExceptionError apiError = GlobalExceptionError.builder()
                .errorCode(e.getErrorCode())
                .timestamp(LocalDateTime.now())
                .field(e.getField())
                .message(e.getMessage())
                .status(HttpStatus.UNAUTHORIZED)
                .build();
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }
}
