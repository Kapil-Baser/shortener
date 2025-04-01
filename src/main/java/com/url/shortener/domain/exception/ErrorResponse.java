package com.url.shortener.domain.exception;

import java.time.LocalDateTime;

public class ErrorResponse {
    String errorMessage;
    String errorDetail;
    LocalDateTime errorAt;

    public ErrorResponse(String errorMessage, String errorDetail) {
        this.errorMessage = errorMessage;
        this.errorDetail = errorDetail;
        this.errorAt = LocalDateTime.now();
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public LocalDateTime getErrorAt() {
        return errorAt;
    }

    public void setErrorAt(LocalDateTime errorAt) {
        this.errorAt = errorAt;
    }

    public String getErrorDetail() {
        return errorDetail;
    }

    public void setErrorDetail(String errorDetail) {
        this.errorDetail = errorDetail;
    }
}
