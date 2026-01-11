package be.brahms.TFE_RentServe.utilities;

import be.brahms.TFE_RentServe.configurations.security.JwtConfig;
import be.brahms.TFE_RentServe.models.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * This class helps with JSON Web Tokens (JWT).
 * It can create, read, and check JWT tokens.
 */
@Component
public class JwtUtil {

    private final JwtConfig jwtConfig; // Configuration for JWT operation
    private final JwtParser jwtParser; // Jwt parser for decoding token
    private final JwtBuilder jwtBuilder;// Jwt builder for generating token

    /**
     * Creates a JwtUtil with the given configuration
     *
     * @param jwtConfig The JwtConfig object with secret key and expiration time.
     */
    public JwtUtil(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
        this.jwtParser = Jwts.parser().verifyWith(jwtConfig.getSecretKey).build();
        this.jwtBuilder = Jwts.builder().signWith(jwtConfig.getSecretKey);
    }

    /**
     * Creates a JWT token for a user.
     *
     * @param user The user who will get the token.
     * @return A JWT token string.
     */
    public String generateToken(User user) {
        return jwtBuilder.claim("pseudo", user.getPseudo())
                .claim("role", user.getRole().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtConfig.expireAt))
                .compact();
    }

    /**
     * Gets all claims (data) from the token.
     *
     * @param token The JWT token.
     * @return Claims (like pseudo and role).
     */
    public Claims getClaims(String token) {
        return jwtParser.parseSignedClaims(token).getPayload();
    }

    /**
     * Gets the pseudo (username) from the token.
     *
     * @param token The JWT token.
     * @return The pseudo string.
     */
    public String getPseudo(String token) {
        return getClaims(token).get("pseudo", String.class);
    }

    /**
     * Checks if the token is still valid (not expired).
     *
     * @param token The JWT token.
     * @return true if the token is valid, false if it is expired or not correct.
     */
    public boolean isValidToken(String token) {
        Claims claims = getClaims(token);
        Date dateNow = new Date();
        return dateNow.after(claims.getIssuedAt()) && dateNow.before((claims.getExpiration()));
    }

}
