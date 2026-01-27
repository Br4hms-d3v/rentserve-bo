package be.brahms.TFE_RentServe.models.dtos.user;

import java.time.LocalDate;

/**
 * UserDto is a data transfer object for user information.
 * It holds user details like name, first name, birthdate, pseudo, email, street, city, and zip code.
 *
 * @param id        the identification of the user
 * @param name      the name of user
 * @param firstName the firstname of user
 * @param birthdate the birthdate of user
 * @param pseudo    the nickname of user
 * @param email     the e-mail of user
 * @param street    the number and name of street where live the user
 * @param city      the name of city
 * @param zipCode   the number of city
 * @param isActive  the boolean for user activate his account
 */
public record UserDTO(
        Long id,
        String name,
        String firstName,
        LocalDate birthdate,
        String pseudo,
        String email,
        String street,
        String city,
        String zipCode,
        boolean isActive
) {
}
