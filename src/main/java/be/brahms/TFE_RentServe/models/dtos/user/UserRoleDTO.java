package be.brahms.TFE_RentServe.models.dtos.user;

import be.brahms.TFE_RentServe.enums.Role;

import java.time.LocalDate;

/**
 * UserRoleDto is a data transfer object for role from user
 * It holds user details like name, firstname, role and created
 *
 * @param name      the name of user
 * @param firstName the firstname of user
 * @param pseudo    the pseudo of user
 * @param role      the role of user
 * @param createdAt the date when user created
 */
public record UserRoleDTO(
        String name,
        String firstName,
        String pseudo,
        Role role,
        LocalDate createdAt
) {
}
