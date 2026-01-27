package be.brahms.TFE_RentServe.models.forms.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * This form is used to submit a password change request
 * It contains the email, the new password and a confirmation of the new password
 *
 * @param email           the user's email address
 * @param password        the new password
 * @param comparePassword the confirmation of the new password, must match password
 * @author Brahim K
 */
public record UserChangePasswordForm(
        @Email(message = "Veuillez entrer une adresse email valide")
        @NotBlank(message = "Veuillez entrer une adresse email")
        String email,
        @NotBlank
        String password,
        @NotBlank
        String comparePassword
) {
}
