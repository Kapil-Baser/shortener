package com.url.shortener.domain.factory;

import com.url.shortener.domain.service.Base62Shortener;
import com.url.shortener.domain.service.ShortenerService;
import com.url.shortener.domain.service.UuidShortener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UrlShorteningFactory {
    private final ShortenerService base62Shortener;
    private final ShortenerService uuidShortener;

    @Autowired
    public UrlShorteningFactory(Base62Shortener base62Shortener, UuidShortener uuidShortener) {
        this.base62Shortener = base62Shortener;
        this.uuidShortener = uuidShortener;
    }

    public String generateShortUrl(String url, String strategy) {
        if ("base62".equals(strategy)) {
            return base62Shortener.generateShortUrl(url);
        } else if ("uuid".equals(strategy)) {
            return uuidShortener.generateShortUrl(url);
        } else {
            throw new IllegalArgumentException("Unknown shortening strategy: " + strategy);
        }
    }
}
