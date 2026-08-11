package com.url.shortener.infrastructure.web.controller;

import com.url.shortener.domain.dto.ShortenUrlRequestDto;
import com.url.shortener.domain.dto.ShortenUrlResponseDto;
import com.url.shortener.domain.dto.UrlDTO;
import com.url.shortener.domain.model.Url;
import com.url.shortener.domain.service.UrlService;
import com.url.shortener.domain.service.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ShortenUrlResponseDto> shortenUrl(@RequestBody ShortenUrlRequestDto dto) {
        UrlDTO urlDto = shortenerService.generateShortUrl(dto.url());
        String shortCode = urlDto.getShortUrl();
        ShortenUrlResponseDto responseDto = new ShortenUrlResponseDto(shortCode);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{url}")
    public ResponseEntity<UrlDTO> getUrlDTOFromShortUrl(@PathVariable("url") String url) {
        UrlDTO dtoFromShortUrl = urlService.getDTOFromShortUrl(url);
        return ResponseEntity.status(HttpStatus.OK).body(dtoFromShortUrl);
    }

    @PutMapping("/{shortUrl}")
    public ResponseEntity<UrlDTO> updateUrl(@PathVariable("shortUrl") String shortUrl, @RequestBody Url url) {
        UrlDTO updatedDTO = urlService.updateUrl(shortUrl, url);
        return ResponseEntity.status(HttpStatus.OK).body(updatedDTO);
    }

    @DeleteMapping("/{shortUrl}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUrl(@PathVariable("shortUrl") String shortUrl) {
        urlService.deleteByShortUrl(shortUrl);
    }

    @GetMapping("/{shortUrl}/stats")
    public ResponseEntity<UrlDTO> stats(@PathVariable("shortUrl") String shortUrl) {
        UrlDTO dto = urlService.getStats(shortUrl);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }
}
