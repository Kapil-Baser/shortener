package com.url.shortener.infrastructure.web.controller;

import com.url.shortener.domain.dto.ShortenUrlRequestDto;
import com.url.shortener.domain.dto.ShortenUrlResponseDto;
import com.url.shortener.domain.dto.UrlStatsDto;
import com.url.shortener.domain.service.UrlService;
import com.url.shortener.domain.service.UrlShortenerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/shorten")
@Tag(
        name = "Url Shortner Controller",
        description = "Controller for creating, updating, deleting and getting a short code for a long url and an endpoint for getting stats for a short code."
)
public class UrlShortenerController {

    private final UrlShortenerService shortenerService;
    private final UrlService urlService;

    @Autowired
    public UrlShortenerController(UrlShortenerService shortenerService, UrlService service) {
        this.shortenerService = shortenerService;
        this.urlService = service;
    }

    @Operation(description = "API responsible for creating a short code of provided url.")
    @PostMapping
    public ResponseEntity<ShortenUrlResponseDto> shortenUrl(@Valid @RequestBody ShortenUrlRequestDto requestDto, UriComponentsBuilder ucb) {
        ShortenUrlResponseDto dto = shortenerService.generateShortUrl(requestDto);
        URI locationOfNewUrl = ucb
                .path("/api/v1/shorten/{shortUrl}")
                .buildAndExpand(dto.shortCode())
                .toUri();
        return ResponseEntity.created(locationOfNewUrl).body(dto);
    }

    @Operation(description = "API responsible for fetching the original url from it's short code.")
    @GetMapping("/{shortCode}")
    public ResponseEntity<ShortenUrlResponseDto> getOriginalUrl(@PathVariable("shortCode") String shortCode) {
        var responseDto = urlService.getUrlDto(shortCode);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @Operation(description = "API responsible for updating an existing url.")
    @PutMapping("/{shortCode}")
    public ResponseEntity<ShortenUrlResponseDto> updateUrl(@PathVariable("shortCode") String shortCode, @Valid @RequestBody ShortenUrlRequestDto dto) {
        var updatedUrl = urlService.updateUrl(shortCode, dto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUrl);
    }

    @Operation(description = "API responsible for deleting a url.")
    @DeleteMapping("/{shortCode}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUrl(@PathVariable("shortCode") String shortCode) {
        urlService.deleteByShortCode(shortCode);
    }

    @Operation(description = "API responsible for returning the stats for a url.")
    @GetMapping("/{shortCode}/stats")
    public ResponseEntity<UrlStatsDto> urlStats(@PathVariable("shortCode") String shortCode) {
        var stats = urlService.getStats(shortCode);
        return ResponseEntity.status(HttpStatus.OK).body(stats);
    }
}
