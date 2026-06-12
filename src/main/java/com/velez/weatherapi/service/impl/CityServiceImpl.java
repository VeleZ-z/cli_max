package com.velez.weatherapi.service.impl;

import com.velez.weatherapi.dto.CityResponseDto;
import com.velez.weatherapi.entity.City;
import com.velez.weatherapi.exception.ResourceNotFoundException;
import com.velez.weatherapi.mapper.CityMapper;
import com.velez.weatherapi.repository.CityRepository;
import com.velez.weatherapi.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    @Override
    @Transactional(readOnly = true)
    public CityResponseDto getCityById(Long id) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("City not found with id " + id));
        return cityMapper.toDto(city);
    }
}
