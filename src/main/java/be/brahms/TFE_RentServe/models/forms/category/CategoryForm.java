package be.brahms.TFE_RentServe.models.forms.category;

import jakarta.validation.constraints.NotBlank;

/**
 * Record CategoryForm into a Category entity
 *
 * @param nameCategory the name of category
 */
public record CategoryForm(
        @NotBlank
        String nameCategory
) {
}
