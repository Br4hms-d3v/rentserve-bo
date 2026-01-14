package be.brahms.TFE_RentServe.models.forms.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * This form is used when a user wants to log in.
 * It takes the email or pseudo and the password.
 *
 * @param email    the user's email (must be valid)
 * @param pseudo   the user's pseudo (optional if email is used)
 * @param password the user's password (cannot be blank)
 */
public record UserLoginForm(
        @Email
        String email,
        String pseudo,
        @NotBlank
        String password
) {
}
