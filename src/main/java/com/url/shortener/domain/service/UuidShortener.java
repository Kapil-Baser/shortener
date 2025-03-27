package com.url.shortener.domain.service;

import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class UuidShortener implements ShortenerService{
    @Override
    public String generateShortUrl(String originalUrl) {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 6);
    }
}
