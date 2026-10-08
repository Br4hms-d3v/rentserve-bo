package be.brahms.TFE_RentServe.models.forms.review;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

/**
 * Record ReviewUpdateForm to edit review for review Entity
 *
 * @param id the identifier review
 * @param comment the comment
 * @param rating the evaluates with a note on the favor or material
 * @param isActive the boolean is active or not
 */
public record ReviewUpdateForm(
    Long id,
    String comment,
    @NotNull @DecimalMin(value = "0.0") @DecimalMax(value = "5.0") Double rating,
    Boolean isActive) {}
