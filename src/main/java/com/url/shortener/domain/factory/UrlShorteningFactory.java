package com.url.shortener.domain.factory;

import com.url.shortener.domain.service.Base62Shortener;
import com.url.shortener.domain.service.ShortenerService;
import com.url.shortener.domain.service.UuidShortener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UrlShorteningFactory {
    private final ShortenerService base62Shortener;
    private final ShortenerService uuidShortener;
    private final Logger logger = LoggerFactory.getLogger(UrlShorteningFactory.class.getName());


    @Autowired
    public UrlShorteningFactory(Base62Shortener base62Shortener, UuidShortener uuidShortener) {
        this.base62Shortener = base62Shortener;
        this.uuidShortener = uuidShortener;
    }

    public String generateShortUrl(String url, String strategy) {
        String shortenedUrl;
        if ("base62".equalsIgnoreCase(strategy)) {
           shortenedUrl = base62Shortener.generateShortUrl(url);
        } else if ("uuid".equalsIgnoreCase(strategy)) {
           shortenedUrl = uuidShortener.generateShortUrl(url);
        } else {
            logger.error("Invalid shortening strategy requested: {}", strategy);
            throw new IllegalArgumentException("Unknown shortening strategy: " + strategy);
        }
        logger.info("Shortening strategy selected: {}", strategy);
        return shortenedUrl;
    }
}
