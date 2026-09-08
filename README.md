# URL Shortening Service

This is a solution to the URL shortener project on roadmap.sh : https://roadmap.sh/projects/url-shortening-service

## Overview
This project is a URL Shortener service that allows users to shorten URLs, retrieve the original URLs from shortened codes, and track statistics such as access counts. It is built using Spring Boot, JPA for persistence, and provides RESTful APIs for interacting with the service.

## Technologies Used

- Java 23
- Spring Boot
- Hibernate/JPA for database interaction
- MySQL database
- Docker to containerize the app
- Maven for dependency management and build automation

## Features

  - Create a short code from a full URL
  - Scans the full URL in case it contains any malicious JavaScript code
  - Retrieve an original URL from a short code
  - Redirects to original URL form a short code
  - Update an existing URL
  - Delete an existing URL
  - Provide statistics like access count of any existing URL

## Prerequisites
  - Java 23 or above
  - MySQL

## To run locally
1. Clone the repository
   ```BASH
   git clone https://github.com/Kapil-Baser/shortener.git
   cd shortener
    ```
2. Configure the Database
    - Update application.properties with your database credentials.
3. Build and Run
    ```
    mvnw spring-boot:run
    ```

## Deployment with Docker

If you have Docker and Docker Compose installed, you can spin up the entire application and database with a single command.

1. **Set up your environment variables:**
   Clone the example environment file and fill in your desired database credentials:
   ```bash
   cp .env.example .env
   ```
   *(Open the newly created `.env` file and adjust the passwords if needed).*

2. **Start the application stack:**
   ```bash
   docker compose up --build
   ```

3. **Access the application:**
   * App URL: `http://localhost:8080`

4. **Stop the containers:**
   ```bash
   docker compose down
   ```


## API Documentation

Interactive Swagger UI: `http://localhost:8080/swagger-ui/index.html`

Raw OpenAPI Description (JSON): `http://localhost:8080/v3/api-docs`

Server Running on : `http://localhost:8080`

| Endpoints                           | Method   | Description                                 |
|-------------------------------------|----------|---------------------------------------------|
| `{shortCode}`                       | `GET`    | **MVC Route:** Redirects directly to the original URL     |
| `/api/v1/shorten`                   | `POST`   | Create a new short URL                      |
| `/api/v1/shorten/{shortCode}`       | `GET`    | Retrieve the original URL from a short code |
| `/api/v1/shorten/{shortCode}`       | `PUT`    | Update an existing short URL                |
| `/api/v1/shorten/{shortCode}`       | `DELETE` | Delete an existing short URL                |
| `/api/v1/shorten/{shortCode}/stats` | `GET`    | Get statistics for a short URL              |
