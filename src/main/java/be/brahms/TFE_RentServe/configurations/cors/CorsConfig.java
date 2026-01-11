package be.brahms.TFE_RentServe.configurations.cors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

/**
 * This class configures Cross-Origin Resource Sharing (CORS) for the application.
 * CORS is a security feature that allows or restricts resources from being requested
 * from another domain outside the domain from which the resource originated.
 * <p>
 * The configuration allows requests from a specific origin and defines which HTTP
 * methods and headers are permitted.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    /**
     * This is a constructor by default
     */
    public CorsConfig() {
    }

    /**
     * This method adds CORS mappings to the application.
     * It allows requests from "<a href="http://localhost:4200">localhost angular</a>" and permits all HTTP methods.
     * It also allows all headers and exposes the "Authorization" header.
     *
     * @param registry the CORS registry to which the mappings are added
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // for all routes
                .allowedOrigins("http://localhost:4200") // Accept only localhost:4200
                .allowedMethods("*") // Accept all methode GET, Put, Patch, Delete
                .allowedHeaders("*") // Accept all Headers
                .exposedHeaders("Authorization") // Important to send the JWT from backend to frontend
                .allowCredentials(true); // This is necessary to use sessions or cookies for authentication
    }

    /**
     * This method creates a CORS configuration source.
     * It sets allowed origins, methods, headers, and other CORS settings.
     *
     * @return a CORS configuration source with the defined settings
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("Get", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(List.of("Authorization"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(Duration.ofHours(1L));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
