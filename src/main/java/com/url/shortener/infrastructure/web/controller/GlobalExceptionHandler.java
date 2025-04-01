package com.url.shortener.infrastructure.web.controller;

import com.url.shortener.domain.exception.ErrorResponse;
import com.url.shortener.domain.exception.InvalidUrlException;
import com.url.shortener.domain.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest webRequest) {
        ErrorResponse resourceNotFoundResponse = new ErrorResponse(ex.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(resourceNotFoundResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidUrlException.class)
    public ResponseEntity<ErrorResponse> handleInvalidUrlException(InvalidUrlException ex, WebRequest webRequest) {
        ErrorResponse invalidUrlResponse = new ErrorResponse(ex.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(invalidUrlResponse, HttpStatus.NOT_ACCEPTABLE);
    }
}
