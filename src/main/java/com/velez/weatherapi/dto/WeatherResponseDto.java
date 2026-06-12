package com.velez.weatherapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "Weather record response")
public class WeatherResponseDto {
    private Long id;
    private String countryName;
    private String cityName;
    private Double temperature;
    private Integer humidity;
    private Double pressure;
    private Double windSpeed;
    private String weatherCondition;
    private LocalDateTime recordedAt;
    private Long cityId;
}
