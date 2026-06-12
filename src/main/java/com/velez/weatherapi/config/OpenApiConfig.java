package com.velez.weatherapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI weatherApiOpenApi() {
        return new OpenAPI().info(new Info()
                .title("Weather API")
                .version("v1")
                .description("REST API to manage city weather records")
                .contact(new Contact().name("Weather API Team")));
    }
}
