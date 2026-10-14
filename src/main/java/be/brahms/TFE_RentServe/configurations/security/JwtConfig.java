package be.brahms.TFE_RentServe.configurations.security;

import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * This class is for JWT (JSON Web Token) configuration. It helps to set up the secret key and
 * expiration time for the JWT.
 */
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {

  /**
   * Default constructor for JwtConfig. This constructor does not take any parameters. It
   * initializes the JwtConfig object.
   */
  public JwtConfig() {}

  /** The secret key used to sign the JWT. It is a byte array that starts with "secret key". */
  private final byte[] secret = System.getenv("SECRET_KEY").getBytes(StandardCharsets.UTF_8);

  //    private final byte[] secret =
  // "Put_Your_Key_To_Clean_Gradle".getBytes(StandardCharsets.UTF_8);

  /**
   * The time in seconds before the JWT expires. This is set to 10.800.000 seconds, which is 3.0
   * hours.
   */
  public int expireAt = 10_800_000;

  /** This method returns the secret key for HMAC SHA-384. It uses the secret byte array. */
  public SecretKey getSecretKey = new SecretKeySpec(secret, "hmacSHA384");
}
