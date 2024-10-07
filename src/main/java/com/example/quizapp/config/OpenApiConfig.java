package com.example.quizapp.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(info = @Info(title = "Documentation for the quiz application", description = "All information of the controller and Methods For Quiz App", version = "1.0"), servers = {
		@Server(description = "Quiz App Backend", url = "http://localhost:7000") }, security = @SecurityRequirement(name = "Token based Authorization"))

@SecuritySchemes({
		@SecurityScheme(name = "Token based Authorization", description = "JWT Auth description", scheme = "bearer", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", in = SecuritySchemeIn.HEADER) })

public class OpenApiConfig {}
