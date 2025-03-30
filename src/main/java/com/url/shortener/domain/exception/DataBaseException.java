package com.url.shortener.domain.exception;

public class DataBaseException extends UrlServiceException{
    public DataBaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
