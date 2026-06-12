package com.velez.weatherapi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.velez.weatherapi.entity.City;
import com.velez.weatherapi.entity.Weather;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class WeatherRepositoryTest {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private WeatherRepository weatherRepository;

    @Test
    void shouldFindLatestWeatherByCountryAndCity() {
        City city = new City();
        city.setCountryName("Colombia");
        city.setName("Medellin");
        city = cityRepository.save(city);

        Weather weather = new Weather();
        weather.setCity(city);
        weather.setTemperature(26.5);
        weather.setHumidity(75);
        weather.setPressure(1012.4);
        weather.setWindSpeed(10.2);
        weather.setWeatherCondition("Cloudy");
        weatherRepository.save(weather);

        Optional<Weather> result = weatherRepository
                .findTopByCityCountryNameIgnoreCaseAndCityNameIgnoreCaseOrderByRecordedAtDesc("Colombia", "Medellin");

        assertThat(result).isPresent();
        assertThat(result.get().getCity().getName()).isEqualTo("Medellin");
    }

    @Test
    void shouldFindCityByCountryAndNameIgnoringCase() {
        City city = new City();
        city.setCountryName("Colombia");
        city.setName("Medellin");
        cityRepository.save(city);

        Optional<City> result = cityRepository.findByCountryNameIgnoreCaseAndNameIgnoreCase("colombia", "medellin");

        assertThat(result).isPresent();
    }
}
