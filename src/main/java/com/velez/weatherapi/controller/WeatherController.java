package com.velez.weatherapi.controller;

import com.velez.weatherapi.dto.CreateWeatherRequestDto;
import com.velez.weatherapi.dto.WeatherResponseDto;
import com.velez.weatherapi.hateoas.WeatherModelAssembler;
import com.velez.weatherapi.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/weather")
@RequiredArgsConstructor
@Validated
public class WeatherController {

    private final WeatherService weatherService;
    private final WeatherModelAssembler weatherModelAssembler;

    @Operation(summary = "Create weather record", description = "Registers weather information for a city")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Weather record created",
                    content = @Content(schema = @Schema(implementation = WeatherResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping
    public ResponseEntity<EntityModel<WeatherResponseDto>> createWeather(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Weather creation payload",
                    required = true,
                    content = @Content(examples = @ExampleObject(value = """
                            {
                              \"countryName\": \"Colombia\",
                              \"cityName\": \"Medellin\",
                              \"temperature\": 26.5,
                              \"humidity\": 75,
                              \"pressure\": 1012.4,
                              \"windSpeed\": 10.2,
                              \"weatherCondition\": \"Cloudy\"
                            }
                            """)))
            @Valid @RequestBody CreateWeatherRequestDto request) {
        WeatherResponseDto weather = weatherService.createWeatherRecord(request);
        EntityModel<WeatherResponseDto> body = weatherModelAssembler.toModel(weather);
        return ResponseEntity
                .created(ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/api/v1/weather")
                        .queryParam("countryName", weather.getCountryName())
                        .queryParam("cityName", weather.getCityName())
                        .build().toUri())
                .body(body);
    }

    @Operation(summary = "Get weather by country and city", description = "Returns latest weather record by country and city")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Weather record found",
                    content = @Content(schema = @Schema(implementation = WeatherResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Weather record not found", content = @Content)
    })
    @GetMapping
    public ResponseEntity<EntityModel<WeatherResponseDto>> getLatestWeather(
            @RequestParam @NotBlank String countryName,
            @RequestParam @NotBlank String cityName) {
        WeatherResponseDto weather = weatherService.getLatestWeatherByCountryAndCity(countryName, cityName);
        return ResponseEntity.status(HttpStatus.OK).body(weatherModelAssembler.toModel(weather));
    }
}
