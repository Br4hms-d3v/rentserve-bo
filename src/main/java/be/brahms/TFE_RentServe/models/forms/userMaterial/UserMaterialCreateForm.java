package be.brahms.TFE_RentServe.models.forms.userMaterial;

import be.brahms.TFE_RentServe.enums.State;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Record UserMaterial CreateForm into a UserMaterial entity
 *
 * @param descriptionMaterial The description about the material
 * @param priceHourMaterial The price to rent per hour
 * @param isAvailable the know if is it available to rent
 * @param materialId the identifier material
 * @param state the state about quality of material
 * @param userId the identifier of user
 */
public record UserMaterialCreateForm(
    @NotBlank String descriptionMaterial,
    @Digits(integer = 4, fraction = 2) // 4 numbers before the dot and 2 after the dot
        @DecimalMin(value = "1.00") // The value can be start 5.0 or more but never under
        BigDecimal priceHourMaterial,
    @NotNull Boolean isAvailable,
    @NotNull Long materialId,
    @NotNull State state,
    //    @NotNull List<String> pictures,
    Long userId) {}
