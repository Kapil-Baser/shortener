package com.url.shortener.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.net.URI;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String TIMESTAMP = "timestamp";

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest webRequest) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());

        problemDetail.setTitle("Resource not found.");
        problemDetail.setType(URI.create("/errors/resource-not-found"));
        problemDetail.setProperty(TIMESTAMP, Instant.now());

        return ResponseEntity.of(problemDetail).build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                "Your request payload has validation errors.");

        problemDetail.setTitle("Validation Failed.");
        problemDetail.setType(URI.create("/errors/validation-failed"));

        Map<String, String> validationErrors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            FieldError fieldError = (FieldError) error;
            String fieldName = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();
            validationErrors.put(fieldName, errorMessage);
        });

        problemDetail.setProperty("errors", validationErrors);
        problemDetail.setProperty(TIMESTAMP, Instant.now());

        return ResponseEntity.of(problemDetail).build();
    }

    @ExceptionHandler(InvalidUrlException.class)
    public ResponseEntity<ProblemDetail> handleInvalidUrlException(InvalidUrlException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                ex.getMessage());

        problemDetail.setTitle("Validation Failed.");
        problemDetail.setType(URI.create("/errors/invalid-url"));
        problemDetail.setProperty(TIMESTAMP, Instant.now());

        return ResponseEntity.of(problemDetail).build();
    }
}
