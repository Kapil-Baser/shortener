package com.url.shortener.domain.exception;

public class InvalidUrlException extends UrlServiceException{
    public InvalidUrlException(String message) {
        super(message);
    }
}
