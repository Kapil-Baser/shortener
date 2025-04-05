package com.url.shortener;

import com.url.shortener.domain.model.Url;
import com.url.shortener.infrastructure.persistence.UrlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UrlRepositoryTest {
    @Autowired
    private UrlRepository urlRepository;

    @BeforeEach
    void setUp() {
        urlRepository.save(new Url("https://example.com", "ab142"));
    }

    @Test
    void testFindByOriginalUrl() {
        Optional<Url> url = urlRepository.findByUrl("https://example.com");
        assertTrue(url.isPresent());
        assertEquals("ab142", url.get().getShortUrl());
    }

    @Test
    void testFindByShortUrl() {
        Optional<Url> url = urlRepository.findByShortUrl("ab142");
        assertTrue(url.isPresent());
        assertEquals("https://example.com", url.get().getUrl());
    }

    @Test
    void testNonExistentUrl() {
        Optional<Url> url = urlRepository.findByUrl("https://nonexistent.com");
        assertFalse(url.isPresent());
    }
}
