package be.brahms.TFE_RentServe.models.forms.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

/**
 * Record UserForm into a User entity
 *
 * @param name      the user’s name
 * @param firstName the user’s firstname
 * @param birthdate the user’s birthdate
 * @param pseudo    the chosen nickname
 * @param email     the email address
 * @param password  password
 * @param street    the street
 * @param city      the city
 * @param zipCode   postal code
 */
public record UserForm(
        @NotBlank
        String name,

        @NotBlank
        String firstName,

        @Past(message = "La date de naissance doit être dans le passé")
        LocalDate birthdate,

        @NotBlank
        String pseudo,

        @NotBlank
        String email,

        @NotBlank
        String password,

        @NotBlank
        String street,

        @NotBlank
        String city,

        @NotBlank
        String zipCode
) {
}
