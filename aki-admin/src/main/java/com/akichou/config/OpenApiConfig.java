package com.akichou.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SpringOpenApi Swagger Configuration
 *
 * @author Aki Chou
 * @date 2024/06/23 Sun.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI springOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("SpringDoc API")
                        .description("SpringDoc Aki Blog Application")
                        .contact(new Contact()
                                .name("Aki Chou")
                                //.url("https://www.google.com/")
                                .email("yoang9487@gmail.com"))
                        .version("0.0.1")) ;
    }
}