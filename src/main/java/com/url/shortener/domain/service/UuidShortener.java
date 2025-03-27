package com.url.shortener.domain.service;

import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class UuidShortener implements ShortenerService{
    @Override
    public String generateShortUrl(String originalUrl) {
        try {
            String safeUrl = UrlSanitizer.sanitizeUrl(originalUrl);
            return UUID.randomUUID().toString().replace("-", "").substring(0, 6);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("URL Validation error: " + e.getMessage());
        }
    }
}
