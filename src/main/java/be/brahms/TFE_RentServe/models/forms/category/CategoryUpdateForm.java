package be.brahms.TFE_RentServe.models.forms.category;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record CategoryUpdateForm(
        @NotBlank
        String nameCategory,
        LocalDate updatedAt
) {
}
