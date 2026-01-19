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
 * @param name
 * @param firstName
 * @param birthdate
 * @param pseudo
 * @param email
 * @param street
 * @param city
 * @param zipCode
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
