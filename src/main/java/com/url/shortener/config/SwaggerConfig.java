package com.url.shortener.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Url Shortner",
                version = "1.0",
                description = "Spring Boot app that converts a long web address into a short, unique alias that still directs users to the original page."
        )
)
@Configuration
public class SwaggerConfig {
}
