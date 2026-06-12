package com.velez.weatherapi.service.impl;

import com.velez.weatherapi.dto.CreateWeatherRequestDto;
import com.velez.weatherapi.dto.WeatherResponseDto;
import com.velez.weatherapi.entity.City;
import com.velez.weatherapi.entity.Weather;
import com.velez.weatherapi.exception.ResourceNotFoundException;
import com.velez.weatherapi.mapper.WeatherMapper;
import com.velez.weatherapi.repository.CityRepository;
import com.velez.weatherapi.repository.WeatherRepository;
import com.velez.weatherapi.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private final WeatherRepository weatherRepository;
    private final CityRepository cityRepository;
    private final WeatherMapper weatherMapper;

    @Override
    @Transactional
    public WeatherResponseDto createWeatherRecord(CreateWeatherRequestDto request) {
        City city = cityRepository.findByCountryNameIgnoreCaseAndNameIgnoreCase(request.getCountryName(), request.getCityName())
                .orElseGet(() -> createCity(request.getCountryName().trim(), request.getCityName().trim()));

        Weather weather = new Weather();
        weather.setCity(city);
        weather.setTemperature(request.getTemperature());
        weather.setHumidity(request.getHumidity());
        weather.setPressure(request.getPressure());
        weather.setWindSpeed(request.getWindSpeed());
        weather.setWeatherCondition(request.getWeatherCondition().trim());

        Weather saved = weatherRepository.save(weather);
        return weatherMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public WeatherResponseDto getLatestWeatherByCountryAndCity(String countryName, String cityName) {
        Weather weather = weatherRepository
                .findTopByCityCountryNameIgnoreCaseAndCityNameIgnoreCaseOrderByRecordedAtDesc(countryName, cityName)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Weather record not found for city '%s' in country '%s'".formatted(cityName, countryName)));
        return weatherMapper.toDto(weather);
    }

    private City createCity(String countryName, String cityName) {
        City city = new City();
        city.setCountryName(countryName);
        city.setName(cityName);
        try {
            return cityRepository.save(city);
        } catch (DataIntegrityViolationException ex) {
            return cityRepository.findByCountryNameIgnoreCaseAndNameIgnoreCase(countryName, cityName)
                    .orElseThrow(() -> ex);
        }
    }
}
