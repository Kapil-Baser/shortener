package com.url.shortener;

import com.url.shortener.domain.exception.ResourceNotFoundException;
import com.url.shortener.domain.model.Url;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UrlShortenerController.class)
public class UrlShortenerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UrlService urlService;
    @MockitoBean
    private UrlShortenerService urlShortenerService;

    @Test
    void deleteUrl_ShouldReturnNoContentStatus_WhenUrlIsDeleted() throws Exception {
        String shortUrl = "abc123";

        mockMvc.perform(delete("/api/v1/shorten/{shortUrl}", shortUrl))
                .andExpect(status().isNoContent());

        verify(urlService, times(1)).deleteByShortUrl(shortUrl);
    }

    @Test
    void deleteUrl_ShouldReturnNotFound_WhenUrlDoesNotExist() throws Exception {
        String shortUrl = "doesnotexist";

        doThrow(new ResourceNotFoundException("URL not found"))
                .when(urlService).deleteByShortUrl(shortUrl);

        mockMvc.perform(delete("/shorten/{shortUrl}", shortUrl))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateANewShortUrl() throws Exception {
        String newUrl = "{\"url\":\"http://www.extest.com\"}";
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/shorten")
                .content(newUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated());

    }
    

}
