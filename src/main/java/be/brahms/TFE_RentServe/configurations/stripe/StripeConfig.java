package be.brahms.TFE_RentServe.configurations.stripe;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Configures the Stripe API.
 */
@Configuration
public class StripeConfig {

    /** constructor by default */
    public StripeConfig(){}

    /**
     * The secret key used to access the Stripe API.
     */
    @Value("${stripe.secret.key}")
    private String secretKey;

    /**
     * Sets the Stripe API key.
     */
    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }
}
