package com.velez.weatherapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Payload to create a weather record")
public class CreateWeatherRequestDto {

    @NotBlank(message = "countryName is required")
    @Schema(example = "Colombia")
    private String countryName;

    @NotBlank(message = "cityName is required")
    @Schema(example = "Medellin")
    private String cityName;

    @NotNull(message = "temperature is required")
    @Schema(example = "26.5")
    private Double temperature;

    @NotNull(message = "humidity is required")
    @Min(value = 0, message = "humidity must be between 0 and 100")
    @Max(value = 100, message = "humidity must be between 0 and 100")
    @Schema(example = "75")
    private Integer humidity;

    @NotNull(message = "pressure is required")
    @Positive(message = "pressure must be positive")
    @Schema(example = "1012.4")
    private Double pressure;

    @NotNull(message = "windSpeed is required")
    @Positive(message = "windSpeed must be positive")
    @Schema(example = "10.2")
    private Double windSpeed;

    @NotBlank(message = "weatherCondition is required")
    @Schema(example = "Cloudy")
    private String weatherCondition;
}
