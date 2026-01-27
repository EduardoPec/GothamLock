package com.wayne.waynesecurity.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Wayne Security API",
                description = "API para gerenciamento de segurança da Wayne Enterprises",
                version = "v1",
                contact = @Contact(
                        name = "Eduardo Peçanha",
                        email = "eduardopecanha05@gmail.com"
                )
        )
)
@SecurityScheme(
        name = "basicAuth",
        description = "Autenticação Basic HTTP",
        scheme = "basic",
        type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
