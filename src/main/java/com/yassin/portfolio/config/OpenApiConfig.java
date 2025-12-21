package com.yassin.portfolio.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Portfolio API")
                        .version("1.0")
                        .description("REST API for my professional portfolio")
                        .contact(new Contact()
                                .name("Yassin Mohamed")
                                .email("yassenm.zafan@gmail.com")
                                .url("https://github.com/YassenMohamed7")));
    }
}