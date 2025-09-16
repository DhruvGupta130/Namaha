package com.trulydesignfirm.namaha.configuration;

import com.trulydesignfirm.namaha.annotation.Mobile;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.PropertyCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI authOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Namaha Api's")
                        .version("1.0.0")
                        .description("A flower delivery service"))
                .addSecurityItem(new SecurityRequirement().addList("BearerAuth"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("BearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }

    @Bean
    public PropertyCustomizer mobilePropertyCustomizer() {
        return (schema, type) -> {
            if (type.getCtxAnnotations() != null && Arrays.stream(type.getCtxAnnotations()).anyMatch(a -> a.annotationType().equals(Mobile.class))) {
                schema.example("+919876543210").description("User mobile number with country code (e.g., +919876543210)");
            }
            return schema;
        };
    }
}
