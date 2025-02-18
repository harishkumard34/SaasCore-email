package com.techpuram.saascore.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex, WebRequest request) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("Code", HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.put("Message", "An unexpected error occurred.");
        errorResponse.put("details", ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Map<String, Object>> handleCustomException(CustomException ex, WebRequest request) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("Code", ex.getCode());
        errorResponse.put("Message", ex.getMessage());
        errorResponse.put("details", ex.getDetails());

        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(ex.getCode()));
    }
}
