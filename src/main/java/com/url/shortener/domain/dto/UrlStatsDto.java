package com.url.shortener.domain.dto;

import java.time.LocalDateTime;

public record UrlStatsDto(String id, String url, String shortCode, LocalDateTime createdAt, LocalDateTime updatedAt, int accessCount) {
}
