package be.brahms.TFE_RentServe.models.forms.category;

import jakarta.validation.constraints.NotBlank;

/**
 *
 * @param nameCategory
 */
public record CategoryForm(
        @NotBlank
        String nameCategory
) {
}
