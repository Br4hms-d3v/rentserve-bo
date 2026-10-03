package be.brahms.TFE_RentServe.models.forms.userMaterial;

import be.brahms.TFE_RentServe.enums.State;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Record UserMaterial into a UserMaterial entity
 *
 * @param descriptionMaterial the text to describe the material
 * @param priceHourMaterial the price to rend per hour
 * @param isAvailable the available of user material
 * @param materialId the identifier of material
 * @param state the state (good or damage ) of material
 */
public record UserMaterialUpdateForm(
    @NotBlank String descriptionMaterial,
    @Digits(integer = 4, fraction = 2) // 4 numbers before the dot and 2 after the dot
        @DecimalMin(value = "1.00") // The value can be start 5.0 or more but never under
        BigDecimal priceHourMaterial,
    @NotNull Boolean isAvailable,
    @NotNull Long materialId,
    @NotNull State state) {}
