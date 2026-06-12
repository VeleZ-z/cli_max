package com.velez.weatherapi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.velez.weatherapi.dto.CreateWeatherRequestDto;
import com.velez.weatherapi.dto.WeatherResponseDto;
import com.velez.weatherapi.entity.City;
import com.velez.weatherapi.entity.Weather;
import com.velez.weatherapi.exception.ResourceNotFoundException;
import com.velez.weatherapi.mapper.WeatherMapper;
import com.velez.weatherapi.repository.CityRepository;
import com.velez.weatherapi.repository.WeatherRepository;
import com.velez.weatherapi.service.impl.WeatherServiceImpl;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WeatherServiceImplTest {

    @Mock
    private WeatherRepository weatherRepository;
    @Mock
    private CityRepository cityRepository;

    private WeatherMapper weatherMapper;

    @InjectMocks
    private WeatherServiceImpl weatherService;

    @BeforeEach
    void setUp() {
        weatherMapper = new WeatherMapper();
        weatherService = new WeatherServiceImpl(weatherRepository, cityRepository, weatherMapper);
    }

    @Test
    void shouldCreateWeatherRecordAndCityWhenCityDoesNotExist() {
        CreateWeatherRequestDto request = new CreateWeatherRequestDto();
        request.setCountryName("Colombia");
        request.setCityName("Medellin");
        request.setTemperature(26.5);
        request.setHumidity(75);
        request.setPressure(1012.4);
        request.setWindSpeed(10.2);
        request.setWeatherCondition("Cloudy");

        City city = new City();
        city.setId(1L);
        city.setCountryName("Colombia");
        city.setName("Medellin");

        Weather weather = new Weather();
        weather.setId(1L);
        weather.setCity(city);
        weather.setTemperature(26.5);
        weather.setHumidity(75);
        weather.setPressure(1012.4);
        weather.setWindSpeed(10.2);
        weather.setWeatherCondition("Cloudy");

        when(cityRepository.findByCountryNameIgnoreCaseAndNameIgnoreCase("Colombia", "Medellin"))
                .thenReturn(Optional.empty());
        when(cityRepository.save(any(City.class))).thenReturn(city);
        when(weatherRepository.save(any(Weather.class))).thenReturn(weather);

        WeatherResponseDto result = weatherService.createWeatherRecord(request);

        assertThat(result.getCityName()).isEqualTo("Medellin");
        assertThat(result.getCountryName()).isEqualTo("Colombia");
        assertThat(result.getTemperature()).isEqualTo(26.5);
    }

    @Test
    void shouldThrowWhenWeatherNotFound() {
        when(weatherRepository.findTopByCityCountryNameIgnoreCaseAndCityNameIgnoreCaseOrderByRecordedAtDesc("Colombia", "Bogota"))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> weatherService.getLatestWeatherByCountryAndCity("Colombia", "Bogota"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Weather record not found");
    }
}
