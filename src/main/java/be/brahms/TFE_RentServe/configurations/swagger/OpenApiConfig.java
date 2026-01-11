package be.brahms.TFE_RentServe.configurations.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for OpenAPI (Swagger) documentation.
 * This sets up JWT Bearer authentication for the API.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Default constructor for OpenApiConfig.
     * This constructor does not take any parameters.
     * It initializes the OpenApiConfig object with default values.
     */
    public OpenApiConfig() {
    }

    /**
     * Creates and configures the OpenAPI documentation.
     * Adds JWT Bearer authentication as a security scheme.
     *
     * @return the custom OpenAPI configuration
     */
    @Bean
    public OpenAPI customOpenApi() {

        final String securitySchemeName = "BearerAuth"; // The name of token

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName)) // Need a token to be used
                .components(
                        new Components().addSecuritySchemes(securitySchemeName, // Explain how the security works and use the same name
                                new SecurityScheme()
                                        .name(securitySchemeName) // The name of security
                                        .type(SecurityScheme.Type.HTTP) // Type of web
                                        .scheme("bearer") // The token bearer
                                        .bearerFormat("JWT")) // the token format is JWT
                );
    }
}
