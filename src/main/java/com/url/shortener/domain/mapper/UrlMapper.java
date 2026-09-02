package com.url.shortener.domain.mapper;

import com.url.shortener.domain.dto.ShortenUrlResponseDto;
import com.url.shortener.domain.dto.UrlStatsDto;
import com.url.shortener.domain.model.Url;

public class UrlMapper {
    private UrlMapper() {}

    public static ShortenUrlResponseDto toDto(Url url) {
        return new ShortenUrlResponseDto(url.getId().toString(),
                url.getUrl(),
                url.getShortCode(),
                url.getCreatedAt(),
                url.getUpdatedAt()
        );
    }

    public static UrlStatsDto toStatsDto(Url url) {
        return new UrlStatsDto(url.getId().toString(),
                url.getUrl(),
                url.getShortCode(),
                url.getCreatedAt(),
                url.getUpdatedAt(),
                url.getAccessCount()
        );
    }
}
