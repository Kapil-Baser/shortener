package com.url.shortener.domain.service;

import com.url.shortener.domain.exception.InvalidUrlException;

import java.net.URI;
import java.net.URL;
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
            throw new InvalidUrlException("JavaScript URLs are not allowed");
        }

        // Check for XSS attempts
        if (containsXssRisks(cleanUrl)) {
            throw new InvalidUrlException("URL contains potential XSS risks");
        }

        // Validate URL structure
        try {
            URL parsedUrl = URI.create(cleanUrl).toURL();

            // Additional protocol checks
            String protocol = parsedUrl.getProtocol().toLowerCase();
            if (!isAllowedProtocol(protocol)) {
                throw new InvalidUrlException("Unsupported URL protocol");
            }

            return cleanUrl;
        } catch (Exception e) {
            throw new InvalidUrlException("Invalid URL: " + e.getMessage());
        }

    }

    private static boolean isJavaScriptUrl(String url) {
        return JAVASCRIPT_PATTERN.matcher(url).find();
    }

    private static boolean containsXssRisks(String url) {
        return XSS_PATTERN.matcher(url).find() ||
                DANGEROUS_CHARS.matcher(url).find();
    }

    private static boolean isAllowedProtocol(String protocol) {
        return protocol.equals("http") ||
                protocol.equals("https");
    }
}
