package be.brahms.TFE_RentServe.models.dtos.rental;

import be.brahms.TFE_RentServe.models.dtos.userFavor.UserFavorNameDTO;
import be.brahms.TFE_RentServe.models.dtos.userMaterial.UserMaterialNameDTO;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Data Transfer Object for a Rental This record is used to transfer data between the rental and
 * server
 *
 * @param rentalCreatedAt The date create on rental
 * @param nameFavor the name of user favor
 * @param nameMaterial the name of user material
 * @param amount the amount
 * @param status the status of bill
 */
public record RentalDetailEarnDTO(
    LocalDate rentalCreatedAt,
    UserFavorNameDTO nameFavor,
    UserMaterialNameDTO nameMaterial,
    BigDecimal amount,
    String status) {}
