package be.brahms.TFE_RentServe.models.forms.userFavor;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Record UserFavor into a UserFavor entity
 *
 * @param descriptionFavor the text to describe the favor
 * @param priceHourFavor the price to rend per hour
 * @param isAvailable the available of user favor
 * @param favorId the identifier of favor
 * @param userId the identifier of user
 */
public record UpdateUserFavorForm(
    @NotBlank String descriptionFavor,
    @Digits(integer = 4, fraction = 2) // 4 numbers before the dot and 2 after the dot
        @DecimalMin(value = "5.0") // The value can be start 5.0 or more but never under
        BigDecimal priceHourFavor,
    @NotNull Boolean isAvailable,
    @NotNull Long favorId,
    Long userId) {}
