package com.url.shortener.infrastructure.web;

import com.url.shortener.domain.factory.UrlShorteningFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UrlShortenerController {

    private final UrlShorteningFactory urlShorteningFactory;

    @Autowired
    public UrlShortenerController(UrlShorteningFactory factory) {
        this.urlShorteningFactory = factory;
    }

    @PostMapping("/shorten")
    @ResponseStatus(HttpStatus.CREATED)
    public String shortenUrl(@RequestParam String url, @RequestParam(defaultValue = "base62") String strategy) {
        return urlShorteningFactory.generateShortUrl(url, strategy);
    }
}
