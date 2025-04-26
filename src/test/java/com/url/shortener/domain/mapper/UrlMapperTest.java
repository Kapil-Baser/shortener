package com.url.shortener.domain.mapper;

import com.url.shortener.domain.dto.UrlDTO;
import com.url.shortener.domain.model.Url;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;

class UrlMapperTest {

    @Test
    void returnsUrlDTO_whenUrlGiven() {
        // Given
        LocalDateTime stamp = LocalDateTime.now();
        Url url = new Url();
        url.setUrl("http://www.example.com");
        url.setShortUrl("abc123");
        url.setId(100L);
        url.setUpdatedAt(stamp);
        url.setCreatedAt(stamp);
        url.setAccessCount(5);

        // When
        UrlDTO dto = UrlMapper.toDTO(url);

        // Then
        assertThat(dto).isNotNull();
        assertThat(dto.getUrl()).isEqualTo(url.getUrl());
        assertThat(dto.getShortUrl()).isEqualTo(url.getShortUrl());
        assertThat(dto.getAccessCount()).isEqualTo(url.getAccessCount());
        assertThat(dto.getId()).asLong().isEqualTo(url.getId());
        assertThat(dto.getCreatedAt()).isEqualTo(url.getCreatedAt().toString());
        assertThat(dto.getUpdatedAt()).isEqualTo(url.getUpdatedAt().toString());
    }
}