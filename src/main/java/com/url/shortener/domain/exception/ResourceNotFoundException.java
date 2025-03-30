package com.url.shortener.domain.exception;

public class ResourceNotFoundException extends UrlServiceException{

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
