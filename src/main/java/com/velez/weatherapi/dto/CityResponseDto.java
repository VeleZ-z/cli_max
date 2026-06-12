package com.velez.weatherapi.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CityResponseDto {
    private Long id;
    private String name;
    private String countryName;
    private LocalDateTime createdAt;
}
