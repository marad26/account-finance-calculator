package com.company.accountfinancecalculator.application.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.ZonedDateTime;
import java.util.StringJoiner;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        StringJoiner stringJoiner = new StringJoiner(" ").add("Invalid value provided:");
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            stringJoiner.add("Field:").add(fieldError.getField());
            stringJoiner.add("Value:").add((CharSequence) fieldError.getRejectedValue());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionResponse.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .httpStatusCode(HttpStatus.BAD_REQUEST.value())
                        .message(stringJoiner.toString())
                        .timestamp(ZonedDateTime.now())
                        .build());
    }
}
