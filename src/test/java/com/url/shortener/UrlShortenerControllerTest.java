package com.url.shortener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.url.shortener.domain.dto.ShortenUrlRequestDto;
import com.url.shortener.domain.dto.ShortenUrlResponseDto;
import com.url.shortener.domain.dto.UrlStatsDto;
import com.url.shortener.domain.exception.InvalidUrlException;
import com.url.shortener.domain.exception.ResourceNotFoundException;
import com.url.shortener.domain.service.UrlService;
import com.url.shortener.domain.service.UrlShortenerService;
import com.url.shortener.infrastructure.web.controller.UrlShortenerController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UrlShortenerController.class)
class UrlShortenerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UrlService urlService;
    @MockitoBean
    private UrlShortenerService urlShortenerService;

    @Test
    void shortenUrl_ShouldReturnCreatedStatusWithResponseBody_WhenShortUrlIsGenerated() throws Exception {
        ShortenUrlRequestDto requestDto = new ShortenUrlRequestDto("https://test.com");
        String jsonBody = new ObjectMapper().writeValueAsString(requestDto);
        ShortenUrlResponseDto responseDto = new ShortenUrlResponseDto("1", requestDto.url(), "abc123", LocalDateTime.now(), LocalDateTime.now());

        when(urlShortenerService.generateShortUrl(requestDto)).thenReturn(responseDto);

        mockMvc.perform(post("/api/v1/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.url").value("https://test.com"))
                .andExpect(jsonPath("$.shortCode").value("abc123"))
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.updatedAt").exists());

        verify(urlShortenerService).generateShortUrl(requestDto);
    }

    @Test
    void shortenUrl_ShouldSetLocationHeaderUsingGeneratedShortCode_WhenShortUrlIsGenerated() throws Exception {
        ShortenUrlRequestDto requestDto = new ShortenUrlRequestDto("https://test.com");
        String jsonBody = new ObjectMapper().writeValueAsString(requestDto);
        ShortenUrlResponseDto responseDto = new ShortenUrlResponseDto("1", requestDto.url(), "abc123", LocalDateTime.now(), LocalDateTime.now());

        when(urlShortenerService.generateShortUrl(requestDto)).thenReturn(responseDto);

        mockMvc.perform(post("/api/v1/shorten")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(header().string("location", "http://localhost/api/v1/shorten/abc123"));
    }

    @Test
    void shortenUrl_ShouldPropagateServiceException_WhenShortUrlCannotBeGenerated() throws Exception {
        ShortenUrlRequestDto requestDto = new ShortenUrlRequestDto("hts://invalid.com");
        String jsonBody = new ObjectMapper().writeValueAsString(requestDto);

        doThrow(new InvalidUrlException("Invalid Url"))
                .when(urlShortenerService).generateShortUrl(requestDto);


        mockMvc.perform(post("/api/v1/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(result -> {
                    assertThat(result.getResolvedException()).isInstanceOf(InvalidUrlException.class);
                })
                .andExpect(jsonPath("$.status").value(400));

        verify(urlShortenerService).generateShortUrl(requestDto);
    }

    @Test
    void deleteUrl_ShouldReturnNoContentStatus_WhenUrlIsDeleted() throws Exception {
        String shortCode = "abc123";

        mockMvc.perform(delete("/api/v1/shorten/{shortCode}", shortCode))
                .andExpect(status().isNoContent());

        verify(urlService, times(1)).deleteByShortCode(shortCode);
    }

    @Test
    void deleteUrl_ShouldReturnNoContentStatus_WhenShortCodeDoesNotExist() throws Exception {
        String shortCode = "doesnotexist";

        doThrow(new ResourceNotFoundException("URL not found"))
                .when(urlService).deleteByShortCode(shortCode);

        mockMvc.perform(delete("/api/v1/shorten/{shortCode}", shortCode))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteUrl_ShouldPropagateNotFoundException_WhenUrlDoesNotExist() throws Exception {
        String shortCode = "invalid";

        doThrow(new ResourceNotFoundException("URL not found"))
                .when(urlService).deleteByShortCode(shortCode);

        mockMvc.perform(delete("/api/v1/shorten/{shortCode}", shortCode))
                .andExpect(result -> {
                    assertThat(result.getResolvedException()).isInstanceOf(ResourceNotFoundException.class);
                })
                .andExpect(jsonPath("$.detail").value("URL not found"));
    }

    @Test
    void urlStats_ShouldReturnOkStatusWithStats_WhenShortCodeExists() throws Exception {
        String shortCode = "abc123";
        UrlStatsDto dto = new UrlStatsDto("1", "https://test.com", shortCode, LocalDateTime.now(), LocalDateTime.now(), 5);

        when(urlService.getStats(shortCode)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/shorten/{shortCode}/stats", shortCode))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.url").value("https://test.com"))
                .andExpect(jsonPath("$.shortCode").value("abc123"))
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.updatedAt").exists())
                .andExpect(jsonPath("$.accessCount").value(5));

        verify(urlService).getStats(shortCode);
    }


    

}
