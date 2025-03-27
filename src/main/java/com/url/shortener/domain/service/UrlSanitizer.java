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


}
