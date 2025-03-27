package com.url.shortener.domain.service;

import org.springframework.stereotype.Service;

@Service
public class Base62Shortener implements ShortenerService{
    private static final String BASE62_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int BASE = 62;

    @Override
    public String generateShortUrl(String originalUrl) {
        try {
            String safeUrl = UrlSanitizer.sanitizeUrl(originalUrl);
            // Here need to get id of safeUrl from database
            long id = System.currentTimeMillis();

            StringBuilder shortUrl = new StringBuilder();

            do {
                shortUrl.insert(0, BASE62_CHARS.charAt((int)(id % BASE)));
                id /= BASE;
            } while (id > 0);

            return shortUrl.toString();

        } catch (IllegalArgumentException e) {
            throw new RuntimeException("URL Validation error: " + e.getMessage());
        }
    }
}
