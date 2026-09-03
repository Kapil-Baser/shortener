package com.url.shortener.domain.mapper;

import com.url.shortener.domain.dto.ShortenUrlResponseDto;
import com.url.shortener.domain.dto.UrlStatsDto;
import com.url.shortener.domain.model.Url;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;

class UrlMapperTest {

    Url url = new Url();

    @BeforeEach
    void setup() {
        LocalDateTime stamp = LocalDateTime.now();
        url.setUrl("http://www.example.com");
        url.setShortCode("abc123");
        url.setId(100L);
        url.setUpdatedAt(stamp);
        url.setCreatedAt(stamp);
        url.setAccessCount(5);
    }

    @Test
    void returnsShortenUrlResponseDto_whenUrlGiven() {
        // When
        ShortenUrlResponseDto dto = UrlMapper.toDto(url);

        // Then
        assertThat(dto).isNotNull();
        assertThat(dto.url()).isEqualTo(url.getUrl());
        assertThat(dto.shortCode()).isEqualTo(url.getShortCode());
        assertThat(dto.id()).asLong().isEqualTo(url.getId());
        assertThat(dto.createdAt()).isEqualTo(url.getCreatedAt().toString());
        assertThat(dto.updatedAt()).isEqualTo(url.getUpdatedAt().toString());
    }

    @Test
    void returnsUrlStatsDto_whenUrlGiven() {
        // When
        UrlStatsDto dto = UrlMapper.toStatsDto(url);

        // Then
        assertThat(dto).isNotNull();
        assertThat(dto.url()).isEqualTo(url.getUrl());
        assertThat(dto.shortCode()).isEqualTo(url.getShortCode());
        assertThat(dto.id()).asLong().isEqualTo(url.getId());
        assertThat(dto.createdAt()).isEqualTo(url.getCreatedAt().toString());
        assertThat(dto.updatedAt()).isEqualTo(url.getUpdatedAt().toString());
        assertThat(dto.accessCount()).isEqualTo(url.getAccessCount());
    }
}