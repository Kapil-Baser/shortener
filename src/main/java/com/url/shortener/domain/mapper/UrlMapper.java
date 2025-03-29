package com.url.shortener.domain.mapper;

import com.url.shortener.domain.dto.UrlDTO;
import com.url.shortener.domain.model.Url;

public class UrlMapper {
    public static UrlDTO toDTO(Url url) {
        UrlDTO dto = new UrlDTO();
        dto.setId(url.getId().toString());
        dto.setOriginalUrl(url.getOriginalUrl());
        dto.setShortUrl(url.getShortUrl());
        dto.setCreatedAt(url.getCreatedAt().toString());
        return dto;
    }
}
