package com.example.bookshopapi.exception.handler;

import com.example.bookshopapi.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ExistedException.class)
    public ResponseEntity<GlobalExceptionError> handleExistedException(ExistedException e) throws Exception {
        GlobalExceptionError apiError = new GlobalExceptionError(HttpStatus.BAD_REQUEST, e.getErrorCode(), e.getMessage(),
                LocalDateTime.now(), e.getField());
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<GlobalExceptionError> handleBadRequestException(BadRequestException e) {
        GlobalExceptionError apiError = GlobalExceptionError.builder()
                .errorCode(e.getErrorCode())
                .timestamp(LocalDateTime.now())
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .field(e.getField()).build();
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<GlobalExceptionError> handleUnAuthorizedException(UnAuthorizedException e) {
        GlobalExceptionError apiError = GlobalExceptionError.builder()
                .errorCode(e.getErrorCode())
                .timestamp(LocalDateTime.now())
                .field(e.getField())
                .message(e.getMessage())
                .status(HttpStatus.UNAUTHORIZED)
                .build();
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<GlobalExceptionError> handleNotFoundException(NotFoundException e) {
        GlobalExceptionError apiError = GlobalExceptionError.builder()
                .errorCode(e.getErrorCode())
                .timestamp(LocalDateTime.now())
                .message(e.getMessage())
                .field(e.getField())
                .status(HttpStatus.NOT_FOUND)
                .build();
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<?> handleFileSizeLimitExceededException(MaxUploadSizeExceededException ex) {
        String errorMessage = ex.getCause().getCause().getMessage();
        errorMessage = "File " + errorMessage.substring(errorMessage.indexOf("exceeds"));
        errorMessage += ". Please upload a smaller file";
        GlobalExceptionError apiError = GlobalExceptionError.builder()
                .errorCode(HttpStatus.BAD_REQUEST.toString())
                .timestamp(LocalDateTime.now())
                .message(errorMessage)
                .field("File")
                .status(HttpStatus.BAD_REQUEST)
                .build();
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
