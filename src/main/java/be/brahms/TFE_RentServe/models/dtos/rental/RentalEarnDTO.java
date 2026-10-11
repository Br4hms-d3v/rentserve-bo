package be.brahms.TFE_RentServe.models.dtos.rental;

import java.math.BigDecimal;

/**
 * Data Transfer Object for a Rental This record is used to transfer data between the rental and
 * server
 *
 * @param totalEarned Total the amount earn
 */
public record RentalEarnDTO(BigDecimal totalEarned) {}
