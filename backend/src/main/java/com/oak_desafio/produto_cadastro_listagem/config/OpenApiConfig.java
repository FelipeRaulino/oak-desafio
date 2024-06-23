package com.oak_desafio.produto_cadastro_listagem.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
            .info(new Info()
                .title("Product Management REST API with Java 21 and Spring Boot 3")
                .version("v1")
                .termsOfService("https://www.apache.org/licenses/LICENSE-2.0")
                .description("This API provides endpoints for managing products, built using Java 21 and Spring Boot 3.")
                .license(new License()
                    .name("Apache 2.0")
                    .url("https://www.apache.org/licenses/LICENSE-2.0")
                )
            );
    }

}
