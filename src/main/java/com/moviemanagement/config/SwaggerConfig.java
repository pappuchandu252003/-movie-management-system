package com.moviemanagement.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.*;
import io.swagger.v3.oas.models.security.*;
import org.springdoc.core.models.GroupedOpenApi; // ADD
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    //  MAIN OPENAPI CONFIG (KEEP YOUR EXISTING)
    @Bean
    public OpenAPI customOpenAPI() {

        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .info(new Info()
                        .title("Movie Management System API")
                        .version("1.0")
                        .description("Spring Boot REST API for Movie Booking System")

                        .contact(new Contact()
                                .name("Movie Team")
                                .email("support@movie.com"))

                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org"))
                )

                // 🔐 ENABLE AUTHORIZE BUTTON
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))

                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name("Authorization")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                );
    }

    // 👤 USER APIs
    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("USER-APIs")
                .pathsToMatch(
                        "/auth/**",
                        "/bookings/**",
                        "/reviews/**"
                )
                .build();
    }

    //  ADMIN APIs
    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("ADMIN-APIs")
                .pathsToMatch(
                        "/movies/**",
                        "/theatres/**",
                        "/shows/**"
                )
                .build();
    }

    //COMMON (OPTIONAL)
    @Bean
    public GroupedOpenApi commonApi() {
        return GroupedOpenApi.builder()
                .group("COMMON-APIs")
                .pathsToMatch(
                        "/users/**"
                )
                .build();
    }
}