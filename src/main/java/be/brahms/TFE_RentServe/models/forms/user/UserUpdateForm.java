package be.brahms.TFE_RentServe.models.forms.user;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

/**
 * This form is used when a user update his profile.
 * The user can change his:
 * name, firstname, birthdate
 * pseudo, email
 * street, city and zipCode
 *
 * @param name      the user's name can be changed
 * @param firstName the user's firstname can be changed
 * @param birthdate the user's birthdate can be changed
 * @param pseudo    the user's pseudo can be changed
 * @param email     the user's email address can be changed
 * @param street    the user's street with number can be changed
 * @param city      the user's city can be changed
 * @param zipCode   the user's zip code can be changed
 */
public record UserUpdateForm(
        @NotBlank
        String name,
        @NotBlank
        String firstName,
        @Past
        @NotNull
        LocalDate birthdate,
        @NotBlank
        @Size(min = 3, max = 150)
        String pseudo,
        @Email
        @NotBlank
        String email,
        @NotBlank
        String street,
        @NotBlank
        String city,
        @NotBlank
        String zipCode
) {
}
