package com.url.shortener.domain.service;

import com.url.shortener.domain.dto.UrlDTO;
import com.url.shortener.domain.exception.DataBaseException;
import com.url.shortener.domain.exception.InvalidUrlException;
import com.url.shortener.domain.exception.ResourceNotFoundException;
import com.url.shortener.domain.mapper.UrlMapper;
import com.url.shortener.domain.model.Url;
import com.url.shortener.infrastructure.persistence.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UrlService {
    private final UrlRepository repository;

    @Autowired
    public UrlService(UrlRepository repository) {
        this.repository = repository;
    }

/*    public UrlDTO getDTOFromOriginalUrl(String originalUrl) {
        if (originalUrl == null || originalUrl.isEmpty()) {
            throw new IllegalArgumentException("URL cannot be null or empty");
        }

        try {
            Optional<Url> url = repository.findByUrl(originalUrl);
            if (url.isEmpty()) {
                throw new ResourceNotFoundException("No DTO found matching the requested URL: " + originalUrl);
            }

            return UrlMapper.toDTO(url.get());
        } catch (DataAccessException e) {
            throw new DataBaseException("Database error while retrieving URL", e);
        }
    }*/

    public UrlDTO getDTOFromShortUrl(String shortUrl) {
        if (shortUrl == null || shortUrl.isEmpty()) {
            throw new IllegalArgumentException("URL cannot be null or empty");
        }

        try {
            Url foundUrl = repository.findByShortUrl(shortUrl)
                    .orElseThrow(
                    () -> new ResourceNotFoundException("Error: No URL found")
            );

            // Updating the access count
            foundUrl.setAccessCount(foundUrl.getAccessCount() + 1);

            // Saving the Url entity back to database before returning
            repository.save(foundUrl);

            return UrlMapper.toDTO(foundUrl);
        } catch (DataAccessException e) {
            throw new DataBaseException("Database error while retrieving URL", e);
        }
    }

    public UrlDTO updateUrl(String shortUrl, Url updatedUrl) {
        if (shortUrl == null || shortUrl.isEmpty()) {
            throw new IllegalArgumentException("URL cannot be null or empty");
        }

        if (updatedUrl == null) {
            throw new InvalidUrlException("URL data cannot be null");
        }

        try {
            Url savedUrl = repository.findByShortUrl(shortUrl)
                    .orElseThrow(() -> new ResourceNotFoundException("Error: No matching URL for requested short url: " + shortUrl)
            );

            // Updating the URL
            savedUrl.setUrl(updatedUrl.getUrl());
            savedUrl.setUpdatedAt(LocalDateTime.now());

            repository.save(savedUrl);

            return UrlMapper.toDTO(savedUrl);
        } catch (DataAccessException e) {
            throw new DataBaseException("Database error while trying to update the URL", e);
        }
    }
}
