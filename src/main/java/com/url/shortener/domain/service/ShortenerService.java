package com.url.shortener.domain.service;

import com.url.shortener.domain.dto.UrlDTO;

public interface ShortenerService {
    UrlDTO generateShortUrl(String originalUrl);
}
