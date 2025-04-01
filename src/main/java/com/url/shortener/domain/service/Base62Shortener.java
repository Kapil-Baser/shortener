package com.url.shortener.domain.service;

import com.url.shortener.domain.dto.UrlDTO;
import com.url.shortener.domain.mapper.UrlMapper;
import com.url.shortener.domain.model.Url;
import com.url.shortener.infrastructure.persistence.UrlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class Base62Shortener implements ShortenerService{
    private static final String BASE62_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int BASE = 62;
    private final UrlRepository repository;
    private final Logger logger = LoggerFactory.getLogger(Base62Shortener.class);

    @Autowired
    public Base62Shortener(UrlRepository repository) {
        this.repository = repository;
    }

    @Override
    public UrlDTO generateShortUrl(String originalUrl) {
        try {
            // Sanitize URL for security
            String safeUrl = UrlSanitizer.sanitizeUrl(originalUrl);

            // Checking if URL already exists in database
            Optional<Url> existingUrl = repository.findByUrl(safeUrl);
            if (existingUrl.isPresent()) {
                logger.info("URL already exists in database, returning existing short URL");
                return UrlMapper.toDTO(existingUrl.get());
            }

            // In case URL does not exist then we create a new URL entity
            Url newUrl = new Url();
            newUrl.setUrl(safeUrl);
            newUrl.setCreatedAt(LocalDateTime.now());

            // Saving the entity first to get the ID
            try {
               newUrl = repository.save(newUrl);
            } catch (DataAccessException e) {
                logger.info("Database error while saving URL", e);
                throw new RuntimeException("Database error while saving URL", e);
            }

            // Getting the ID and then will generate the short URL
            long id = newUrl.getId();
            String shortUrl = encodeToBase62(id);
            newUrl.setShortUrl(shortUrl);

            // Updating the entity with the shortUrl
            try {
                newUrl = repository.save(newUrl);
            } catch (DataAccessException e) {
                logger.info("Database error while updating the URL", e);
                throw new RuntimeException("Database error while updating the URL", e);
            }

            logger.info("Successfully generated the short URL: {} for Original URL: {}", shortUrl, safeUrl);

            return UrlMapper.toDTO(newUrl);
        } catch (IllegalArgumentException e) {
            logger.warn("URL validation error", e);
            throw new RuntimeException("URL Validation error: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error in URL shortening service", e);
            throw new RuntimeException("Failed to process URL shortening request", e);
        }
    }

    private String encodeToBase62(long id) {
        if (id == 0) {
            return String.valueOf(BASE62_CHARS.charAt(0));
        }

        StringBuilder shortUrl = new StringBuilder();
        long number = id;

        while (number > 0) {
            shortUrl.insert(0, BASE62_CHARS.charAt((int)(number % BASE)));
            number /= BASE;
        }

        return shortUrl.toString();
    }
}
