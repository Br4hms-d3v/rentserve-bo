package be.brahms.TFE_RentServe.models.dtos.stripe;

/**
 * Data for a checkout session.
 *
 * @param sessionId the ID of the session
 * @param checkoutUrl the URL to pay
 */
public record CheckoutSessionDTO(String sessionId, String checkoutUrl) {}
