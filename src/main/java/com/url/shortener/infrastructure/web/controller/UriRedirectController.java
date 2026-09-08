package com.url.shortener.infrastructure.web.controller;

import com.url.shortener.domain.service.UrlService;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UriRedirectController {

    private final UrlService urlService;

    public UriRedirectController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> getRedirectionUri(@PathVariable("shortCode") String shortCode) {
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(urlService.getRedirectionUri(shortCode))
                .build();
    }
}
