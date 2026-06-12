package com.velez.weatherapi.controller;

import com.velez.weatherapi.dto.CityResponseDto;
import com.velez.weatherapi.hateoas.CityModelAssembler;
import com.velez.weatherapi.service.CityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cities")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;
    private final CityModelAssembler cityModelAssembler;

    @Operation(summary = "Get city by ID", description = "Returns city details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "City found"),
            @ApiResponse(responseCode = "404", description = "City not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<CityResponseDto>> getCityById(@PathVariable Long id) {
        CityResponseDto city = cityService.getCityById(id);
        return ResponseEntity.ok(cityModelAssembler.toModel(city));
    }
}
