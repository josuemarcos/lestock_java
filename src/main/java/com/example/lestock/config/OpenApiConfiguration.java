package com.example.lestock.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Lestock API",
                version = "v1",
                contact = @Contact(
                        name = "Josué Fernandes",
                        url = "https://github.com/josuemarcos",
                        email = "josue.fernandes@ee.ufcg.edu.br"
                ),
                description = "Products stock management API"
        )

)
public class OpenApiConfiguration {
}
