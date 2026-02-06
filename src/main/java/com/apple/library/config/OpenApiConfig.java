package com.apple.library.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    private static final String BASIC_AUTH = "basicAuth";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(
                        new Components().addSecuritySchemes(
                                BASIC_AUTH,
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("basic")
                        )
                )
                .addSecurityItem(
                        new SecurityRequirement().addList(BASIC_AUTH)
                )
                .info(
                        new Info()
                                .title("Library Management API")
                                .version("1.0")
                                .description("Production-grade Library Service with Basic Authentication")
                );
    }
}
