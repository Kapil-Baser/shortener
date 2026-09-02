package com.url.shortener.domain.service;

import com.url.shortener.domain.dto.ShortenUrlRequestDto;
import com.url.shortener.domain.dto.ShortenUrlResponseDto;
import com.url.shortener.domain.dto.UrlStatsDto;
import com.url.shortener.domain.exception.ResourceNotFoundException;
import com.url.shortener.domain.mapper.UrlMapper;
import com.url.shortener.domain.model.Url;
import com.url.shortener.infrastructure.persistence.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UrlService {
    private final UrlRepository repository;

    @Autowired
    public UrlService(UrlRepository repository) {
        this.repository = repository;
    }

    public ShortenUrlResponseDto getOriginalUrl(String shortCode) {
        Url foundUrl = findByShortCode(shortCode);

        // Updating the access count
        foundUrl.setAccessCount(foundUrl.getAccessCount() + 1);

        // Saving the Url entity back to database before returning
        repository.save(foundUrl);

        return UrlMapper.toDto(foundUrl);

    }

    public ShortenUrlResponseDto updateUrl(String shortCode, ShortenUrlRequestDto requestDto) {
        Url url = findByShortCode(shortCode);

        String urlToUpdate = UrlSanitizer.sanitizeUrl(requestDto.url());

        // Updating the URL
        url.setUrl(urlToUpdate);

        Url updatedUrl = repository.save(url);

        return UrlMapper.toDto(updatedUrl);
    }


    public void deleteByShortCode(String shortCode) {
        Url savedUrl = findByShortCode(shortCode);

        repository.delete(savedUrl);
    }

    public UrlStatsDto getStats(String shortCode) {
        Url savedUrl = findByShortCode(shortCode);

        return UrlMapper.toStatsDto(savedUrl);
    }

    private Url findByShortCode(String shortCode) {
        return repository.findByShortCode(shortCode)
                .orElseThrow(
                        () -> new ResourceNotFoundException("URL not found for given shortCode: " + shortCode)
                );
    }
}
