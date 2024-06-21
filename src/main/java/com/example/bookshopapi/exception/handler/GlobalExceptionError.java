package com.example.bookshopapi.exception.handler;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
public class GlobalExceptionError {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private String errorCode;
    private HttpStatus status;
    private String field;

    public GlobalExceptionError(final HttpStatus status, final String errorCode, final String message, LocalDateTime timestamp,
                                final String field) {
        super();
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
        this.errorCode = errorCode;
        this.field = field;
    }
    //
}