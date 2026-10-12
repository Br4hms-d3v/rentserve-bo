package be.brahms.TFE_RentServe.models.forms.user;

import be.brahms.TFE_RentServe.enums.Role;
import jakarta.validation.constraints.NotNull;

/**
 * Form used to update a user's role.
 *
 * @param role the new role to assign to the user
 */
public record UserRoleForm(@NotNull Role role) {}
