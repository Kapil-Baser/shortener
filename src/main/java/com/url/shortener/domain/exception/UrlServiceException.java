package com.url.shortener.domain.exception;

public abstract class UrlServiceException extends RuntimeException{
    public UrlServiceException(String message) {
        super(message);
    }

    public UrlServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
