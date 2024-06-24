package com.example.bookshopapi.exception.handler;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GlobalExceptionError {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime timestamp;
    private HttpStatus status;
    private String errorCode;
    private String message;
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