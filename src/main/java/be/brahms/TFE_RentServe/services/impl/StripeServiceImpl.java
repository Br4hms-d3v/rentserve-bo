package be.brahms.TFE_RentServe.services.impl;

import be.brahms.TFE_RentServe.enums.Status;
import be.brahms.TFE_RentServe.exceptions.bill.BillException;
import be.brahms.TFE_RentServe.exceptions.bill.BillNotFoundException;
import be.brahms.TFE_RentServe.models.dtos.stripe.CheckoutSessionDTO;
import be.brahms.TFE_RentServe.models.entities.Bill;
import be.brahms.TFE_RentServe.models.entities.Rental;
import be.brahms.TFE_RentServe.repositories.BillRepository;
import be.brahms.TFE_RentServe.services.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;
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

    // Calculate 2% fees, rounded to 2 decimal places
    BigDecimal fees = amount.multiply(new BigDecimal("0.10")).setScale(2, RoundingMode.HALF_UP);

    // Stripe retrieves the amount in cents
    long amountInCents = amount.movePointRight(2).longValueExact();

    long feesInCents = fees.movePointRight(2).longValueExact();

    // Create the session for Stripe Checkout
    SessionCreateParams.Builder params =
        SessionCreateParams.builder()
            .setMode(SessionCreateParams.Mode.PAYMENT)
            .setSuccessUrl(frontendUrl + "/payment/success?session_id={CHECKOUT_SESSION_ID} &billId="
                    + billId)
            .setCancelUrl(frontendUrl + "/payment/cancel")
            .setClientReferenceId(String.valueOf(bill.getId()))
            .putMetadata("bill_id", String.valueOf(bill.getId()))
            // First row for the price
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
                                    .setDescription(buildRentalDescription(bill))
                                    .build())
                            .build())
                    .build());

    // Add the 2% fees as a separate line item
    if (feesInCents > 0) {
      params
          .addLineItem(
              SessionCreateParams.LineItem.builder()
                  .setQuantity(1L)
                  .setPriceData(
                      SessionCreateParams.LineItem.PriceData.builder()
                          .setCurrency("eur")
                          .setUnitAmount(feesInCents)
                          .setProductData(
                              SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                  .setName("Frais d’assurance du matériel et de gestion (2 %)")
                                  .build())
                          .build())
                  .build())
          .build();
    }

    Session session = Session.create(params.build());

    return new CheckoutSessionDTO(session.getId(), session.getUrl());
  }

  public void confirmPayment(String sessionId) throws StripeException {
    // Check the status of session from Stripe
    Session session = Session.retrieve(sessionId);



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

  /**
   * Builds a description of the rentals on a bill.
   *
   * @param bill the bill to describe
   * @return the rental descriptions with dates and times
   */
  private String buildRentalDescription(Bill bill) {

    StringBuilder description = new StringBuilder();

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    if (bill.getRentals() == null || bill.getRentals().isEmpty()) {
      return "Facture n° : " + bill.getId();
    }

    for (Rental rental : bill.getRentals()) {

      // Séparer chaque matériel ou service par une ligne vide
      if (description.length() > 0) {
        description.append("\n\n");
      }

      // Matériel
      if (rental.getUserMaterial() != null) {
        description.append("Matériel : ").append(rental.getUserMaterial().getDescriptionMaterial());
      }
      // Service
      else if (rental.getUserFavor() != null) {
        description.append("Service : ").append(rental.getUserFavor().getDescriptionFavor());
      }
      // Aucun des deux
      else {
        description.append("Location");
      }

      // Date de début : LocalDate
      if (rental.getStarDateAt() != null) {
        description.append(" | Début : ").append(rental.getStarDateAt().format(dateFormatter));
      }

      // Heure de début : LocalTime
      if (rental.getStartTime() != null) {
        description.append(" à ").append(rental.getStartTime().format(timeFormatter));
      }

      // Date de fin : LocalDate
      if (rental.getEndDateAt() != null) {
        description.append(" | Fin : ").append(rental.getEndDateAt().format(dateFormatter));
      }

      // Heure de fin : LocalTime
      if (rental.getEndTime() != null) {
        description.append(" à ").append(rental.getEndTime().format(timeFormatter));
      }
    }

    return description.toString().trim();
  }
}
