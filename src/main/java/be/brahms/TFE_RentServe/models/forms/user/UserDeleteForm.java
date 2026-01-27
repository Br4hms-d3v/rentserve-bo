package be.brahms.TFE_RentServe.models.forms.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Delete soft user
 * Change boolean isActive from true to false
 *
 * @param email    the email of user
 * @param password the password of user
 */
public record UserDeleteForm(
        @Email(message = "Veuillez entrer une adresse email valide")
        @NotBlank(message = "Veuillez entrer une adresse email")
        String email,
        @NotBlank
        String password
) {
}
