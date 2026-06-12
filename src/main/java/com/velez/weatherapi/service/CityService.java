package com.velez.weatherapi.service;

import com.velez.weatherapi.dto.CityResponseDto;

public interface CityService {
    CityResponseDto getCityById(Long id);
}
