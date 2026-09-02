package com.url.shortener.infrastructure.web.controller;

import com.url.shortener.domain.dto.ShortenUrlRequestDto;
import com.url.shortener.domain.dto.ShortenUrlResponseDto;
import com.url.shortener.domain.dto.UrlStatsDto;
import com.url.shortener.domain.service.UrlService;
import com.url.shortener.domain.service.UrlShortenerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/shorten")
public class UrlShortenerController {

    private final UrlShortenerService shortenerService;
    private final UrlService urlService;

    @Autowired
    public UrlShortenerController(UrlShortenerService shortenerService, UrlService service) {
        this.shortenerService = shortenerService;
        this.urlService = service;
    }

    @PostMapping
    public ResponseEntity<ShortenUrlResponseDto> shortenUrl(@Valid @RequestBody ShortenUrlRequestDto requestDto, UriComponentsBuilder ucb) {
        ShortenUrlResponseDto dto = shortenerService.generateShortUrl(requestDto);
        URI locationOfNewUrl = ucb
                .path("/api/v1/shorten/{shortUrl}")
                .buildAndExpand(dto.shortCode())
                .toUri();
        return ResponseEntity.created(locationOfNewUrl).body(dto);
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<ShortenUrlResponseDto> getOriginalUrl(@PathVariable("shortCode") String shortCode) {
        var responseDto = urlService.getOriginalUrl(shortCode);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @PutMapping("/{shortCode}")
    public ResponseEntity<ShortenUrlResponseDto> updateUrl(@PathVariable("shortCode") String shortCode, @Valid @RequestBody ShortenUrlRequestDto dto) {
        var updatedUrl = urlService.updateUrl(shortCode, dto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUrl);
    }

    @DeleteMapping("/{shortCode}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUrl(@PathVariable("shortCode") String shortCode) {
        urlService.deleteByShortCode(shortCode);
    }

    @GetMapping("/{shortCode}/stats")
    public ResponseEntity<UrlStatsDto> urlStats(@PathVariable("shortCode") String shortCode) {
        var stats = urlService.getStats(shortCode);
        return ResponseEntity.status(HttpStatus.OK).body(stats);
    }
}
