package com.url.shortener.domain.exception;

public class UrlNotFoundException extends UrlServiceException{
    public UrlNotFoundException(String message) {
        super(message);
    }
}
