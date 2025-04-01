package com.url.shortener.infrastructure.persistence;

import com.url.shortener.domain.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {
    Optional<Url> findByUrl(String url);
    Optional<Url> findByShortUrl(String shortUrl);
}
