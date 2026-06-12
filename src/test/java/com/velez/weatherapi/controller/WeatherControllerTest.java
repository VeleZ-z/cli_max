package com.velez.weatherapi.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.velez.weatherapi.dto.CreateWeatherRequestDto;
import com.velez.weatherapi.dto.WeatherResponseDto;
import com.velez.weatherapi.hateoas.WeatherModelAssembler;
import com.velez.weatherapi.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(WeatherController.class)
class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private WeatherService weatherService;

    @MockBean
    private WeatherModelAssembler weatherModelAssembler;

    @Test
    void shouldCreateWeatherRecord() throws Exception {
        CreateWeatherRequestDto request = new CreateWeatherRequestDto();
        request.setCountryName("Colombia");
        request.setCityName("Medellin");
        request.setTemperature(26.5);
        request.setHumidity(75);
        request.setPressure(1012.4);
        request.setWindSpeed(10.2);
        request.setWeatherCondition("Cloudy");

        WeatherResponseDto response = WeatherResponseDto.builder()
                .id(1L)
                .countryName("Colombia")
                .cityName("Medellin")
                .cityId(1L)
                .temperature(26.5)
                .humidity(75)
                .pressure(1012.4)
                .windSpeed(10.2)
                .weatherCondition("Cloudy")
                .build();

        when(weatherService.createWeatherRecord(any(CreateWeatherRequestDto.class))).thenReturn(response);
        when(weatherModelAssembler.toModel(any(WeatherResponseDto.class))).thenReturn(EntityModel.of(response));

        mockMvc.perform(post("/api/v1/weather")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.cityName").value("Medellin"));
    }

    @Test
    void shouldGetWeatherByCountryAndCity() throws Exception {
        WeatherResponseDto response = WeatherResponseDto.builder()
                .id(1L)
                .countryName("Colombia")
                .cityName("Medellin")
                .cityId(1L)
                .temperature(26.5)
                .humidity(75)
                .pressure(1012.4)
                .windSpeed(10.2)
                .weatherCondition("Cloudy")
                .build();

        when(weatherService.getLatestWeatherByCountryAndCity(eq("Colombia"), eq("Medellin"))).thenReturn(response);
        when(weatherModelAssembler.toModel(any(WeatherResponseDto.class))).thenReturn(EntityModel.of(response));

        mockMvc.perform(get("/api/v1/weather")
                        .param("countryName", "Colombia")
                        .param("cityName", "Medellin"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cityName").value("Medellin"));
    }
}
