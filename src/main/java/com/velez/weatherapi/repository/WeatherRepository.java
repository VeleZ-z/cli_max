package com.velez.weatherapi.repository;

import com.velez.weatherapi.entity.Weather;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherRepository extends JpaRepository<Weather, Long> {
    Optional<Weather> findTopByCityCountryNameIgnoreCaseAndCityNameIgnoreCaseOrderByRecordedAtDesc(String countryName, String cityName);
}
