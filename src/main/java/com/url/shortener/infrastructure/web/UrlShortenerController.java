package com.url.shortener.infrastructure.web;

import com.url.shortener.domain.dto.UrlDTO;
import com.url.shortener.domain.factory.UrlShorteningFactory;
import com.url.shortener.domain.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UrlShortenerController {

    private final UrlShorteningFactory urlShorteningFactory;
    private final UrlService urlService;

    @Autowired
    public UrlShortenerController(UrlShorteningFactory factory, UrlService service) {
        this.urlShorteningFactory = factory;
        this.urlService = service;
    }

    @PostMapping("/shorten")
/*    @ResponseStatus(HttpStatus.CREATED)*/
    public ResponseEntity<UrlDTO> shortenUrl(@RequestParam String url, @RequestParam(defaultValue = "base62") String strategy) {
        UrlDTO dto = urlShorteningFactory.generateShortUrl(url, strategy);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping("/shorten/{url}")
    public ResponseEntity<UrlDTO> getUrlDTOFromShortUrl(@PathVariable("url") String url) {
        UrlDTO dtoFromOriginalUrl = urlService.getDTOFromShortUrl(url);
        return ResponseEntity.status(HttpStatus.OK).body(dtoFromOriginalUrl);
    }
}
