package com.example.pcconfigurator.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "PC Configurator API",
                version = "1.0",
                description = "REST API for digital service that helps users assemble compatible PC builds"
        )
)
public class OpenApiConfig {
}

