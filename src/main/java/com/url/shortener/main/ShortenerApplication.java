package com.url.shortener.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class ShortenerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShortenerApplication.class, args);
	}

}
