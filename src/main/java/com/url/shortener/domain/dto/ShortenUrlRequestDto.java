package com.url.shortener.domain.dto;

import jakarta.validation.constraints.NotEmpty;

public record ShortenUrlRequestDto(@NotEmpty(message = "URL cannot be empty or null") String url) {
}
