package be.brahms.TFE_RentServe.models.forms.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

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
