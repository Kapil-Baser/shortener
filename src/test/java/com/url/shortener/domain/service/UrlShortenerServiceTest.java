package com.url.shortener.domain.service;

import com.url.shortener.domain.dto.ShortenUrlRequestDto;
import com.url.shortener.domain.dto.ShortenUrlResponseDto;
import com.url.shortener.domain.model.Url;
import com.url.shortener.infrastructure.persistence.UrlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UrlShortenerServiceTest {

    private static final String SHORT_CODE = "abc12314";
    private Url url = new Url();

    @Mock
    private UrlRepository repository;

    @InjectMocks
    private UrlShortenerService urlShortenerService;

    @Captor
    private ArgumentCaptor<Url> captor;

    @BeforeEach
    void setup() {
        url.setUrl("http://test.com");
        url.setShortCode(SHORT_CODE);
        url.setId(1L);
        url.setAccessCount(0);
        url.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void generateShortUrl_shouldReturnExistingUrlWhenUrlAlreadyExists() {
        ShortenUrlRequestDto requestDto = new ShortenUrlRequestDto("http://test.com");
        when(repository.findByUrl(requestDto.url())).thenReturn(Optional.of(url));

        ShortenUrlResponseDto responseDto = urlShortenerService.generateShortUrl(requestDto);

        assertThat(responseDto).isNotNull();
        assertThat(responseDto.url()).isEqualTo(requestDto.url());
        assertThat(responseDto.shortCode()).isEqualTo(url.getShortCode());

        verify(repository, never()).save(any());

        verify(repository).findByUrl(requestDto.url());
    }

    @Test
    void generateShortUrl_shouldCreateAndSaveNewUrlWhenUrlDoesNotExist() {
        ShortenUrlRequestDto requestDto = new ShortenUrlRequestDto("http://test.com");
        when(repository.findByUrl(requestDto.url())).thenReturn(Optional.empty());

        when(repository.save(any(Url.class))).thenReturn(url);

        ShortenUrlResponseDto responseDto = urlShortenerService.generateShortUrl(requestDto);

        assertThat(responseDto).isNotNull();
        assertThat(responseDto.url()).isEqualTo(requestDto.url());
        assertThat(responseDto.shortCode()).hasSize(8);

        verify(repository).save(captor.capture());

        Url savedUrl = captor.getValue();

        assertThat(savedUrl.getUrl()).isEqualTo(requestDto.url());
        assertThat(savedUrl.getShortCode()).hasSize(8);

        verify(repository).findByUrl(requestDto.url());
        verify(repository).save(any(Url.class));
    }

}