package com.velez.weatherapi.service;

import com.velez.weatherapi.dto.CreateWeatherRequestDto;
import com.velez.weatherapi.dto.WeatherResponseDto;

public interface WeatherService {
    WeatherResponseDto createWeatherRecord(CreateWeatherRequestDto request);
    WeatherResponseDto getLatestWeatherByCountryAndCity(String countryName, String cityName);
}
