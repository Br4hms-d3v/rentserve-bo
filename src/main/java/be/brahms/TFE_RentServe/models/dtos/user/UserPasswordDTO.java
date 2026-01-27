package be.brahms.TFE_RentServe.models.dtos.user;

import jakarta.validation.constraints.NotBlank;

/**
 * UserPasswordDTO used for user password operations.
 * This DTO is mainly used when changing or validating a user's password.
 *
 * @param id    the user's identifier
 * @param email the user's email address
 */
public record UserPasswordDTO(
        Long id,
        @NotBlank
        String email
) {
}
