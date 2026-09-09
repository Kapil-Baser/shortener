package com.url.shortener.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record ShortenUrlResponseDto(
        @Schema(
                example = "1"
        )
        String id,
        @Schema(
                example = "https://example.com/some-long-url"
        )
        String url,
        @Schema(
                example = "abc123"
        )
        String shortCode,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}
