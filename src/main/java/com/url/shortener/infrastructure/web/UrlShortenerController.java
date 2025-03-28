package com.url.shortener.infrastructure.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UrlShortenerController {

    @PostMapping("/shorten")
    public String shortenUrl(@RequestParam String url, @RequestParam(defaultValue = "base62") String strategy) {
        return "";
    }
}
