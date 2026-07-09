package com.ipd.Gestion_Scolaire.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Gestion Scolaire API",
                version = "1.0.0",
                description = "API REST pour la gestion des etudiants, enseignants, cours, inscriptions et authentification JWT.",
                contact = @Contact(
                        name = "Equipe IPD",
                        email = "support@ipd.com"
                ),
                license = @License(
                        name = "Proprietary"
                )
        ),
        servers = {
                @Server(url = "http://localhost:8085", description = "Serveur local")
        }
)
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
