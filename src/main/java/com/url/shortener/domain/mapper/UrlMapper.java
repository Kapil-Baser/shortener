package com.url.shortener.domain.mapper;

import com.url.shortener.domain.dto.UrlDTO;
import com.url.shortener.domain.model.Url;

public class UrlMapper {
    private UrlMapper() {}

    public static UrlDTO toDTO(Url url) {
        UrlDTO dto = new UrlDTO();
        dto.setId(url.getId().toString());
        dto.setUrl(url.getUrl());
        dto.setShortUrl(url.getShortCode());
        dto.setCreatedAt(url.getCreatedAt().toString());
        dto.setAccessCount(url.getAccessCount());
        return dto;
    }
}
