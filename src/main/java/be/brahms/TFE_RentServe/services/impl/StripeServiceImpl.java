package be.brahms.TFE_RentServe.services.impl;

import be.brahms.TFE_RentServe.enums.Status;
import be.brahms.TFE_RentServe.exceptions.bill.BillException;
import be.brahms.TFE_RentServe.exceptions.bill.BillNotFoundException;
import be.brahms.TFE_RentServe.models.dtos.stripe.CheckoutSessionDTO;
import be.brahms.TFE_RentServe.models.entities.Bill;
import be.brahms.TFE_RentServe.repositories.BillRepository;
import be.brahms.TFE_RentServe.services.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Service to manage Stripe payments for bills. */
@Service
public class StripeServiceImpl implements StripeService {

  private final BillRepository billRepository;

  @Value("${app.frontend.url}")
  private String frontendUrl;

  /**
   * Creates the Stripe service.
   *
   * @param billRepository the repository for bills
   */
  public StripeServiceImpl(BillRepository billRepository) {
    this.billRepository = billRepository;
  }

  /**
   * Creates a checkout session for a bill.
   *
   * @param billId the ID of the bill
   * @return the checkout session data
   * @throws StripeException if an error occurs with Stripe
   */
  @Override
  @Transactional(readOnly = true)
  public CheckoutSessionDTO createCheckoutSession(long billId) throws StripeException {

    // Retrieve the bill
    Bill bill =
        billRepository
            .findById(billId)
            .orElseThrow(() -> new IllegalArgumentException("Facture introuvable : "));

    // Check the status of bill
    if (bill.getStatus() == Status.PAID) {
      throw new BillException("Cette facture est déjà payée");
    }

    // Check the amount
    BigDecimal amount = bill.getAmount();

    if (amount.compareTo(BigDecimal.ZERO) < 0) {
      throw new BillException("Le montant de la facture doit être positif");
    }

    // Stripe retrieves the amount in cents
    long amountInCents = amount.movePointRight(2).longValueExact();

    // Create the session for Stripe Checkout
    SessionCreateParams params =
        SessionCreateParams.builder()
            .setMode(SessionCreateParams.Mode.PAYMENT)
            .setSuccessUrl(frontendUrl + "/payment/success?session_id={CHECKOUT_SESSION_ID}")
            .setCancelUrl(frontendUrl + "/payment/cancel")
            .setClientReferenceId(String.valueOf(bill.getId()))
            .putMetadata("bill_id", String.valueOf(bill.getId()))
            .addLineItem(
                SessionCreateParams.LineItem.builder()
                    .setQuantity(1L)
                    .setPriceData(
                        SessionCreateParams.LineItem.PriceData.builder()
                            .setCurrency("eur")
                            .setUnitAmount(amountInCents)
                            .setProductData(
                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                    .setName("Facture n° : " + bill.getId())
                                    .build())
                            .build())
                    .build())
            .build();

    Session session = Session.create(params);

    return new CheckoutSessionDTO(session.getId(), session.getUrl());
  }

  public void confirmPayment(String sessionId) throws StripeException {
    // Check the status of session from Stripe
    Session session = Session.retrieve(sessionId);

    if (!"paid".equals(session.getPaymentStatus())) {
      throw new BillException("Le paiement n'est pas confirmé");
    }

    String billId = session.getMetadata() != null ? session.getMetadata().get("bill_id") : null;

    if (billId == null) {
      throw new BillNotFoundException();
    }

    Bill bill =
        billRepository.findById(Long.parseLong(billId)).orElseThrow(BillNotFoundException::new);

    bill.setStatus(Status.PAID);
    bill.setIsPaid(true);
    billRepository.save(bill);
  }
}
