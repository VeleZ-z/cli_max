package com.velez.weatherapi.mapper;

import com.velez.weatherapi.dto.WeatherResponseDto;
import com.velez.weatherapi.entity.Weather;
import org.springframework.stereotype.Component;

@Component
public class WeatherMapper {

    public WeatherResponseDto toDto(Weather weather) {
        return WeatherResponseDto.builder()
                .id(weather.getId())
                .countryName(weather.getCity().getCountryName())
                .cityName(weather.getCity().getName())
                .temperature(weather.getTemperature())
                .humidity(weather.getHumidity())
                .pressure(weather.getPressure())
                .windSpeed(weather.getWindSpeed())
                .weatherCondition(weather.getWeatherCondition())
                .recordedAt(weather.getRecordedAt())
                .cityId(weather.getCity().getId())
                .build();
    }
}
