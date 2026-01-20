package be.brahms.TFE_RentServe.models.dtos.user;

import jakarta.validation.constraints.NotBlank;

public record UserPasswordDTO(
        Long id,
        @NotBlank
        String email
) {
}
