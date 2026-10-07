package be.brahms.TFE_RentServe.models.forms.review;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

/**
 * Record
 *
 * @param id the identifier review
 * @param comment the comment
 * @param userFavorID the identifier of user favor
 * @param rating the evaluates with a note on the material or favor
 * @param isActive The review is available
 */
public record ReviewUserFavorForm(
    Long id,
    Long userFavorID,
    String comment,
    @NotNull @DecimalMin(value = "0.0") @DecimalMax(value = "5.0") Double rating,
    Boolean isActive) {}
