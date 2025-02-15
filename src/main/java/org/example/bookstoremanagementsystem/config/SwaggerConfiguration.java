package org.example.bookstoremanagementsystem.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Bookstore Management System")
                        .version("1.0.0")
                        .description("API documentation for the Bookstore Management System")
                        .contact(new Contact().name("Bookstore Team").email("bookstore@example.com")));
    }
}
