package be.brahms.TFE_RentServe.configurations.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * This class configures security for the application.
 * It sets which URLs need login and which roles can access them.
 * It also adds JWT filter to check tokens in requests.
 */
@Configuration
@EnableWebSecurity
public class SecurityConf {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    /**
     * Constructor security configuration by default
     */
    public SecurityConf() {

    }

    /**
     * Configures the security rules for HTTP requests.
     *
     * @param http the security object to set rules on
     * @return the configured security filter chain
     * @throws Exception if configuration fails
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // Authentification
                        .requestMatchers("/api/auth/**").permitAll()
                        // Users
                        .requestMatchers("/api/user/list").hasRole("ADMIN")
                        .requestMatchers("/api/user/list/{role}").hasRole("ADMIN")
                        .requestMatchers("/api/user/**").hasAnyRole("MEMBER", "MODERATOR", "ADMIN")
                        .anyRequest().permitAll()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /**
     * Creates a password encoder using BCrypt.
     *
     * @return a BCryptPasswordEncoder object
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Provides the AuthenticationManager for login authentication.
     *
     * @param authenticationConfiguration the configuration to get AuthenticationManager from
     * @return the AuthenticationManager object
     * @throws Exception if getting manager fails
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
