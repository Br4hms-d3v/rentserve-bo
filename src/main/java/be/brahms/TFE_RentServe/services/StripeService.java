package be.brahms.TFE_RentServe.services;

import be.brahms.TFE_RentServe.models.dtos.stripe.CheckoutSessionDTO;
import com.stripe.exception.StripeException;

/** Service to manage Stripe payments. */
public interface StripeService {

  /**
   * Creates a checkout session for a bill.
   *
   * @param billId the ID of the bill
   * @return the checkout session data
   * @throws StripeException if an error occurs with Stripe
   */
  CheckoutSessionDTO createCheckoutSession(long billId) throws StripeException;

  /**
   * Confirms a payment.
   *
   * @param sessionId the ID of the session
   * @throws StripeException if an error occurs with Stripe
   */
  void confirmPayment(String sessionId) throws StripeException;
}
