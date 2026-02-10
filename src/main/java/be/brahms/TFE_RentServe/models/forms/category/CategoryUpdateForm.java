package be.brahms.TFE_RentServe.models.forms.category;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

/**
 * Record CategoryUpdateForm into Category entity
 *
 * @param nameCategory the name of category
 * @param updatedAt    the date when is updated
 */
public record CategoryUpdateForm(
        @NotBlank
        String nameCategory,
        LocalDate updatedAt
) {
}
