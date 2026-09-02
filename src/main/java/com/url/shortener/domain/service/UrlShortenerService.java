package com.url.shortener.domain.service;

import com.url.shortener.domain.dto.ShortenUrlRequestDto;
import com.url.shortener.domain.dto.ShortenUrlResponseDto;
import com.url.shortener.domain.mapper.UrlMapper;
import com.url.shortener.domain.model.Url;
import com.url.shortener.infrastructure.persistence.UrlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class UrlShortenerService{
    private final UrlRepository repository;
    private final Logger logger = LoggerFactory.getLogger(UrlShortenerService.class);

    @Autowired
    public UrlShortenerService(UrlRepository repository) {
        this.repository = repository;
    }

    public ShortenUrlResponseDto generateShortUrl(ShortenUrlRequestDto dto) {
        String originalUrl = dto.url();


        String safeUrl = UrlSanitizer.sanitizeUrl(originalUrl);

        Url url = repository.findByUrl(safeUrl)
                .orElseGet(() -> {
                    String shortCode = generateShortCode();

                    Url newUrl = new Url();
                    newUrl.setShortCode(shortCode);
                    newUrl.setUrl(safeUrl);

                    logger.info("Successfully generated the short URL: {} for Original URL: {}", newUrl.getShortCode(), safeUrl);
                    return repository.save(newUrl);
                });

        return UrlMapper.toDto(url);
    }

    private String generateShortCode() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 8);
    }
}
