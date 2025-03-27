package com.url.shortener.domain.service;

import java.util.regex.Pattern;

public class UrlSanitizer {
    private static final Pattern JAVASCRIPT_PATTERN = Pattern.compile(
            "javascript:", Pattern.CASE_INSENSITIVE
    );

    private static final Pattern XSS_PATTERN = Pattern.compile(
            "(<script.*?>|on\\\\w+\\\\s*=|javascript:)", Pattern.CASE_INSENSITIVE
    );

    private static final Pattern DANGEROUS_CHARS = Pattern.compile(
            "[<>&\"']"
    );

    public static String sanitizeUrl(String originalUrl) {
        if (originalUrl == null || originalUrl.trim().isEmpty()) {
            throw new IllegalArgumentException("URL cannot be null or empty");
        }

        // Remove leading and trailing whitespace
        String cleanUrl = originalUrl.trim();

        // Check for JavaScript based attacks
        if (isJavaScriptUrl(cleanUrl)) {
            throw new IllegalArgumentException("JavaScript URLs are not allowed");
        }


    }

    private static boolean isJavaScriptUrl(String url) {
        return JAVASCRIPT_PATTERN.matcher(url).find();
    }
}
