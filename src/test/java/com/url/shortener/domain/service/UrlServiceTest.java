package com.url.shortener.domain.service;

import com.url.shortener.domain.dto.ShortenUrlResponseDto;
import com.url.shortener.domain.dto.UrlStatsDto;
import com.url.shortener.domain.model.Url;
import com.url.shortener.infrastructure.persistence.UrlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UrlServiceTest {

    private static final String SHORT_CODE = "abc123";
    private Url url = new Url();

    @Mock
    private UrlRepository repository;

    @InjectMocks
    private UrlService urlService;

    @BeforeEach
    void setup() {
        url.setUrl("http://test.com");
        url.setShortCode(SHORT_CODE);
        url.setId(1L);
        url.setAccessCount(0);
        url.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void getOriginalUrl_shouldReturnOriginalUrlAndIncrementAccessCount() {

        when(repository.findByShortCode(SHORT_CODE)).thenReturn(Optional.of(url));

        ShortenUrlResponseDto result = urlService.getOriginalUrl(SHORT_CODE);

        ArgumentCaptor<Url> captor = ArgumentCaptor.forClass(Url.class);

        assertThat(result).isNotNull();
        assertThat(result.url()).isEqualTo("http://test.com");

        verify(repository).save(captor.capture());

        Url savedUrl = captor.getValue();

        assertThat(savedUrl.getAccessCount()).isEqualTo(1);
    }

    @Test
    void deleteByShortCode_shouldDeleteUrlWithGivenShortCode() {

        when(repository.findByShortCode(SHORT_CODE)).thenReturn(Optional.of(url));

        urlService.deleteByShortCode(SHORT_CODE);

        ArgumentCaptor<Url> captor = ArgumentCaptor.forClass(Url.class);

        verify(repository).delete(captor.capture());

        Url deletedUrl = captor.getValue();

        assertThat(deletedUrl.getId()).isEqualTo(url.getId());

        verify(repository).delete(url);
    }

    @Test
    void getStats_shouldReturnUrlStatsForGivenShortCode() {
        when(repository.findByShortCode(SHORT_CODE)).thenReturn(Optional.of(url));

        UrlStatsDto statsDto = urlService.getStats(SHORT_CODE);

        assertThat(statsDto).isNotNull();
        assertThat(statsDto.shortCode()).isEqualTo(SHORT_CODE);

        verify(repository).findByShortCode(SHORT_CODE);
    }

}