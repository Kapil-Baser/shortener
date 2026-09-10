package com.url.shortener.infrastructure.web.controller;

import com.url.shortener.domain.exception.ResourceNotFoundException;
import com.url.shortener.domain.service.UrlService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UriRedirectController.class)
class UriRedirectControllerTest {

    private static final String SHORT_CODE = "abc12314";
    private static final String URL = "http://test.com";
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UrlService urlService;

    @Test
    void getRedirectionUri_ShouldReturnFoundAndLocationHeader() throws Exception {
        URI originalUri = URI.create(URL);
        when(urlService.getRedirectionUri(SHORT_CODE)).thenReturn(originalUri);

        mockMvc.perform(get("/{shortCode}", SHORT_CODE))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", URL));

        verify(urlService).getRedirectionUri(SHORT_CODE);
    }

    @Test
    void getRedirectionUri_ShouldBubbleUpException_WhenShortCodeNotFound() throws Exception {
        when(urlService.getRedirectionUri(SHORT_CODE))
                .thenThrow(new ResourceNotFoundException("URL Not found."));

        mockMvc.perform(get("/{shortCode}", SHORT_CODE))
                .andExpect(status().isNotFound());

        verify(urlService).getRedirectionUri(SHORT_CODE);
    }
}