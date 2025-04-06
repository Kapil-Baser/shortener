# URL Shortening Service

This is a solution to the URL shortener project on roadmap.sh : https://roadmap.sh/projects/url-shortening-service

## Overview
This project is a URL Shortener service that allows users to shorten URLs, retrieve the original URLs from shortened codes, and track statistics such as access counts. It is built using Spring Boot, JPA for persistence, and provides RESTful APIs for interacting with the service.

## Technologies Used

- Java 23
- Spring Boot
- Hibernate/JPA for database interaction
- MySQL database
- Maven for dependency management and build automation

## Features

  - Create a short URL from a full URL
  - Scans the full URL in case it contains any malicious JavaScript code
  - Retrieve an original URL from a shortened URL
  - Update an existing URL
  - Delete an existing URL
  - Provide statistics like access count of any existing URL

## Prerequisites
  - Java 17 or above
  - Docker

## API Documentation

Server Running on : `http://localhost:8080`

| Endpoints          | Method    | Description             |
| ------------------ | --------- | ----------------------- |
| `/api/v1/shorten`  | `POST`    | Create a new short URL |
| `/api/v1/shorten/{shorturl}` | `GET`    | Retrieve the original URL from a short URL |
| `/api/v1/shorten/{shorturl}` | `PUT`    | Update an existing short URL |
| `/api/v1/shorten/{shorturl}` | `DELETE` | Delete an existing short URL |
| `/api/v1/shorten/{shorturl}/stats`  | `GET`  | Get statistics for a short URL |
