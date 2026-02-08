package be.brahms.TFE_RentServe.configurations.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * This class is for JWT (JSON Web Token) configuration.
 * It helps to set up the secret key and expiration time for the JWT.
 */
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {

    /**
     * Default constructor for JwtConfig.
     * This constructor does not take any parameters.
     * It initializes the JwtConfig object.
     */
    public JwtConfig() {
    }

    /**
     * The secret key used to sign the JWT.
     * It is a byte array that starts with "secret key".
     */
//    private final byte[] secret = System.getenv("SECRET_KEY").getBytes(StandardCharsets.UTF_8);
    private final byte[] secret = "ThisIsAnSecretForAppRentServeWithSpringbootAndAngularWithSHA384".getBytes(StandardCharsets.UTF_8);

    /**
     * The time in seconds before the JWT expires.
     * This is set to 5.400.000 seconds, which is 1.5 hours.
     */
    public int expireAt = 5_400_000;

    /**
     * This method returns the secret key for HMAC SHA-384.
     * It uses the secret byte array.
     */
    public SecretKey getSecretKey = new SecretKeySpec(secret, "hmacSHA384");
}
