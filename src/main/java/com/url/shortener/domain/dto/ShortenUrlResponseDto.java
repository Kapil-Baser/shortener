package com.url.shortener.domain.dto;

import java.time.LocalDateTime;

public record ShortenUrlResponseDto(String id, String url, String shortCode, LocalDateTime createdAt, LocalDateTime updatedAt) {
}
