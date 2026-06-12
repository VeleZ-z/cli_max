package com.velez.weatherapi.mapper;

import com.velez.weatherapi.dto.CityResponseDto;
import com.velez.weatherapi.entity.City;
import org.springframework.stereotype.Component;

@Component
public class CityMapper {

    public CityResponseDto toDto(City city) {
        return CityResponseDto.builder()
                .id(city.getId())
                .name(city.getName())
                .countryName(city.getCountryName())
                .createdAt(city.getCreatedAt())
                .build();
    }
}
