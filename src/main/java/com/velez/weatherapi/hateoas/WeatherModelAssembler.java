package com.velez.weatherapi.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.velez.weatherapi.controller.CityController;
import com.velez.weatherapi.controller.WeatherController;
import com.velez.weatherapi.dto.WeatherResponseDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class WeatherModelAssembler implements RepresentationModelAssembler<WeatherResponseDto, EntityModel<WeatherResponseDto>> {

    @Override
    public EntityModel<WeatherResponseDto> toModel(WeatherResponseDto weather) {
        return EntityModel.of(weather,
                linkTo(methodOn(WeatherController.class)
                        .getLatestWeather(weather.getCountryName(), weather.getCityName())).withSelfRel(),
                linkTo(methodOn(CityController.class).getCityById(weather.getCityId())).withRel("city"),
                linkTo(methodOn(WeatherController.class)
                        .getLatestWeather(weather.getCountryName(), weather.getCityName())).withRel("allWeatherRecords"));
    }
}
