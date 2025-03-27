package com.url.shortener.domain.service;

import org.springframework.stereotype.Service;

@Service
public class Base62Shortener implements ShortenerService{
    private static final String BASE62_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int BASE = 62;

    @Override
    public String generateShortUrl(String originalUrl) {
        long id = System.currentTimeMillis();
        StringBuilder shortUrl = new StringBuilder();
        while (id > 0) {
            shortUrl.append(BASE62_CHARS.charAt((int)(id % BASE)));
            id /= BASE;
        }
        return shortUrl.reverse().toString();
    }
}
