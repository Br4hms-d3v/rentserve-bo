package be.brahms.TFE_RentServe.models.dtos.user;

import be.brahms.TFE_RentServe.enums.Role;

import java.time.LocalDate;

/**
 * DTO for user token data.
 * It gives basic user info and a token.
 *
 * @param id        the user identifier of user
 * @param name      the user's name
 * @param firstName the user's firstname
 * @param birthdate the user's birthdate
 * @param email     the user's email
 * @param role      the user's role
 * @param isActive  indicate whether the user account is active
 * @param token     the authenticate token
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
