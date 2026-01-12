package be.brahms.TFE_RentServe.models.dtos.user;

import be.brahms.TFE_RentServe.enums.Role;

import java.time.LocalDate;

/**
 * DTO for user token data.
 * It gives basic user info and a token.
 */

public record UserTokenDTO(
        Long id,
        String name,
        String firstName,
        LocalDate birthdate,
        String email,
        Role role,
        Boolean isActive,
        String token
) {
}
