package com.velez.weatherapi.controller;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.velez.weatherapi.dto.CityResponseDto;
import com.velez.weatherapi.hateoas.CityModelAssembler;
import com.velez.weatherapi.service.CityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CityController.class)
class CityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CityService cityService;

    @MockBean
    private CityModelAssembler cityModelAssembler;

    @Test
    void shouldGetCityById() throws Exception {
        CityResponseDto response = CityResponseDto.builder()
                .id(1L)
                .countryName("Colombia")
                .name("Medellin")
                .build();

        when(cityService.getCityById(anyLong())).thenReturn(response);
        when(cityModelAssembler.toModel(response)).thenReturn(EntityModel.of(response));

        mockMvc.perform(get("/api/v1/cities/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Medellin"));
    }
}
