package com.company.accountfinancecalculator.application.exceptionHandler;

import lombok.Builder;
import lombok.Value;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Value
@Builder
class ExceptionResponse {

    ZonedDateTime timestamp;
    HttpStatus httpStatus;
    int httpStatusCode;
    String message;
}
