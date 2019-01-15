package com.example.money.exception.handler;

import com.example.money.domain.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
            = { Exception.class, RuntimeException.class })
    protected ErrorResponse handleGeneralException(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Internal Server Error";
        return new ErrorResponse(bodyOfResponse);
    }
}
