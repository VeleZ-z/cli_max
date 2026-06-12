package com.velez.weatherapi.repository;

import com.velez.weatherapi.entity.City;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
    Optional<City> findByCountryNameIgnoreCaseAndNameIgnoreCase(String countryName, String name);
}
