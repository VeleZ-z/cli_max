package com.velez.weatherapi.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.velez.weatherapi.controller.CityController;
import com.velez.weatherapi.controller.WeatherController;
import com.velez.weatherapi.dto.CityResponseDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class CityModelAssembler implements RepresentationModelAssembler<CityResponseDto, EntityModel<CityResponseDto>> {

    @Override
    public EntityModel<CityResponseDto> toModel(CityResponseDto city) {
        return EntityModel.of(city,
                linkTo(methodOn(CityController.class).getCityById(city.getId())).withSelfRel(),
                linkTo(methodOn(WeatherController.class).getLatestWeather(city.getCountryName(), city.getName()))
                        .withRel("latestWeather"));
    }
}
