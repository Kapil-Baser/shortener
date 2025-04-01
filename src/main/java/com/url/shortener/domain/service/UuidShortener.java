package com.url.shortener.domain.service;

import com.url.shortener.domain.dto.UrlDTO;
import com.url.shortener.domain.exception.DataBaseException;
import com.url.shortener.domain.exception.InvalidUrlException;
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
import java.util.UUID;

@Service
public class UuidShortener implements ShortenerService{
    private final UrlRepository repository;
    private final Logger logger = LoggerFactory.getLogger(UuidShortener.class);

    @Autowired
    public UuidShortener(UrlRepository repository) {
        this.repository = repository;
    }

    @Override
    public UrlDTO generateShortUrl(String originalUrl) {
        try {
            String safeUrl = UrlSanitizer.sanitizeUrl(originalUrl);

            Optional<Url> existingUrl = repository.findByUrl(safeUrl);
            if (existingUrl.isPresent()) {
                logger.info("Requested URL already exists in database, returning the existing URL");
                return UrlMapper.toDTO(existingUrl.get());
            }

            logger.info("URL does not exist in database so creating a new one");
            Url newUrl = new Url();
            newUrl.setUrl(safeUrl);
            newUrl.setCreatedAt(LocalDateTime.now());
            // Setting the generated short URL
            newUrl.setShortUrl(UUID.randomUUID().toString().replace("-", "").substring(0, 6));

            logger.info("Saving the URL to database before returning the DTO");
            try {
                newUrl = repository.save(newUrl);
            } catch (DataAccessException e) {
                logger.info("Error while saving URL to database", e);
                throw new DataBaseException("Error while saving URL to database", e);
            }

            logger.info("Successfully saved URL to database");
            logger.info("Successfully generated the short URL: {} for Original URL: {}", newUrl.getShortUrl(), safeUrl);
            return UrlMapper.toDTO(newUrl);
        } catch (IllegalArgumentException | InvalidUrlException e ) {
            throw new RuntimeException("URL Validation error: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error while saving URL: " + e.getMessage());
        }
    }
}
