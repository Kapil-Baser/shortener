package com.url.shortener.domain.service;

import com.url.shortener.domain.model.Url;
import com.url.shortener.infrastructure.persistence.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UuidShortener implements ShortenerService{
    private final UrlRepository repository;

    @Autowired
    public UuidShortener(UrlRepository repository) {
        this.repository = repository;
    }

    @Override
    public String generateShortUrl(String originalUrl) {
        try {
            String safeUrl = UrlSanitizer.sanitizeUrl(originalUrl);

            Optional<Url> existingUrl = repository.findByOriginalUrl(safeUrl);
            if (existingUrl.isPresent()) {
                return existingUrl.get().getShortUrl();
            }

            String shortUrl = UUID.randomUUID().toString().replace("-", "").substring(0, 6);

            Url generatedUrl = new Url(safeUrl, shortUrl);
            repository.save(generatedUrl);

            return shortUrl;
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("URL Validation error: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error while saving URL: " + e.getMessage());
        }
    }
}
