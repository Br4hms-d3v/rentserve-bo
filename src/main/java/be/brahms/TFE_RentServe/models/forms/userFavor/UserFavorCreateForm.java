package be.brahms.TFE_RentServe.models.forms.userFavor;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Record UserFavor CreateForm into a UserFavor entity
 *
 * @param descriptionFavor The description about the favor
 * @param priceHourFavor The price to rent per hour
 * @param isAvailable the know if is it available to rent
 * @param favorId the identifier favor
 * @param userId the identifier of user
 */
public record UserFavorCreateForm(
    @NotBlank String descriptionFavor,
    @Digits(integer = 4, fraction = 2) // 4 numbers before the dot and 2 after the dot
        @DecimalMin(value = "5.0") // The value can be start 5.0 or more but never under
        BigDecimal priceHourFavor,
    @NotNull Boolean isAvailable,
    @NotNull Long favorId,
    Long userId) {}
