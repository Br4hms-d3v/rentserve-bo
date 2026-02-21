package be.brahms.TFE_RentServe.models.dtos.favor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Record UpdateFavorForm to update into a Favor Entity
 *
 * @param nameFavor   The name of favor
 * @param category    The name of category
 * @param isAvailable The boolean truth is available for user and false it's not display for user
 */
public record UpdateFavorFormDTO(
        @NotBlank
        String nameFavor,
        @NotNull
        String category,
        @NotNull
        Boolean isAvailable
) {
}
