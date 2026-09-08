package com.url.shortener.domain.exception;

import com.url.shortener.infrastructure.web.controller.UriRedirectController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.view.RedirectView;

@ControllerAdvice(assignableTypes = UriRedirectController.class)
public class GlobalMvcExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public RedirectView handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new RedirectView("/error/url-not-found.html");
    }
}
