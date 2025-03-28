package com.url.shortener.infrastructure.web;

import com.url.shortener.domain.factory.UrlShorteningFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UrlShortenerController {

    private final UrlShorteningFactory urlShorteningFactory;

    @Autowired
    public UrlShortenerController(UrlShorteningFactory factory) {
        this.urlShorteningFactory = factory;
    }

    @PostMapping("/shorten")
    public String shortenUrl(@RequestParam String url, @RequestParam(defaultValue = "base62") String strategy) {
        return "";
    }
}
