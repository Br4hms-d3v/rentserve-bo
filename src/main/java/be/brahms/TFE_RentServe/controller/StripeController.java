package be.brahms.TFE_RentServe.controller;

import be.brahms.TFE_RentServe.models.dtos.stripe.CheckoutSessionDTO;
import be.brahms.TFE_RentServe.services.StripeService;
import com.stripe.exception.StripeException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/** Controller to manage Stripe payments. */
@Controller
@RequestMapping("api/stripe/")
@CrossOrigin("http://localhost:4200/")
public class StripeController {

  private final StripeService stripeService;

  /**
   * Creates the Stripe controller.
   *
   * @param stripeService the Stripe service
   */
  public StripeController(StripeService stripeService) {
    this.stripeService = stripeService;
  }

  /**
   * Creates a payment session for a bill.
   *
   * @param id the ID of the bill
   * @return the checkout session data
   * @throws StripeException if an error occurs with Stripe
   */
  @PostMapping("{id}/pay")
  public ResponseEntity<CheckoutSessionDTO> pay(@PathVariable long id) throws StripeException {
    CheckoutSessionDTO session = stripeService.createCheckoutSession(id);
    return ResponseEntity.ok(session);
  }

  /**
   * Confirms a payment.
   *
   * @param sessionId the ID of the Stripe session
   * @return a message to confirm the payment
   * @throws StripeException if an error occurs with Stripe
   */
  @GetMapping("payment/success")
  public ResponseEntity<String> success(@RequestParam("sessionId") String sessionId)
      throws StripeException {
    stripeService.confirmPayment(sessionId);
    return ResponseEntity.ok("Paiement confirmé");
  }
}
