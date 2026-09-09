package com.url.shortener.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

public record ShortenUrlRequestDto(
        @Schema(
                example = "https://example.com/some-long-url"
        )
        @NotEmpty(message = "URL cannot be empty or null") String url) {
}
