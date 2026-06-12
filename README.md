# Weather API - Spring Boot + PostgreSQL + HATEOAS

REST API to register and query weather information by country and city name.

## Stack

- Java 21
- Spring Boot 3
- Maven
- PostgreSQL (Neon compatible)
- Spring Data JPA
- Spring Validation
- Spring HATEOAS
- Springdoc OpenAPI (Swagger UI)
- Lombok
- JUnit 5 + Mockito

## Project structure

```text
src/main/java/com/velez/weatherapi
├── config
├── controller
├── dto
├── entity
├── exception
├── hateoas
├── mapper
├── repository
└── service
```

## Setup

### 1) Configure Neon DB credentials

Edit `/src/main/resources/application.properties`:

- `spring.datasource.url`
- `spring.datasource.username`
- `spring.datasource.password`

Example URL format:

`jdbc:postgresql://<neon-host>/<database>?sslmode=require`

### 2) Run the application

```bash
mvn spring-boot:run
```

The API starts by default on `http://localhost:8080`.

## API Versioning

URI versioning is used:

- `/api/v1/weather`
- `/api/v1/cities`

## Endpoints

### Create weather record

`POST /api/v1/weather`

Request:

```json
{
  "countryName": "Colombia",
  "cityName": "Medellin",
  "temperature": 26.5,
  "humidity": 75,
  "pressure": 1012.4,
  "windSpeed": 10.2,
  "weatherCondition": "Cloudy"
}
```

Response: `201 Created`

### Get latest weather by country and city

`GET /api/v1/weather?countryName=Colombia&cityName=Medellin`

Response includes HATEOAS links (`self`, `city`, `allWeatherRecords`).

### Get city by id

`GET /api/v1/cities/{id}`

## Swagger / OpenAPI

Available at:

- `http://localhost:8080/swagger-ui.html`
- `http://localhost:8080/swagger-ui/index.html`

OpenAPI metadata is configured in `OpenApiConfig`.

## Validation rules

- `countryName`, `cityName`, `weatherCondition`: required, not blank
- `temperature`: required
- `humidity`: 0 to 100
- `pressure`, `windSpeed`: positive

## Error handling

Global exception handling with `@RestControllerAdvice` returns standardized JSON:

```json
{
  "timestamp": "",
  "status": 404,
  "error": "Not Found",
  "message": "",
  "path": ""
}
```

## Testing

Run tests:

```bash
mvn test
```

Includes:

- Service unit tests
- Controller tests
- Repository tests
