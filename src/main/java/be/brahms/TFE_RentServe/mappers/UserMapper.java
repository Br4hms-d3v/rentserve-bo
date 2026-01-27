package be.brahms.TFE_RentServe.mappers;

import be.brahms.TFE_RentServe.models.dtos.user.UserDTO;
import be.brahms.TFE_RentServe.models.dtos.user.UserPasswordDTO;
import be.brahms.TFE_RentServe.models.dtos.user.UserRoleDTO;
import be.brahms.TFE_RentServe.models.entities.User;
import be.brahms.TFE_RentServe.models.forms.user.UserChangePasswordForm;
import be.brahms.TFE_RentServe.models.forms.user.UserDeleteForm;
import be.brahms.TFE_RentServe.models.forms.user.UserUpdateForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper responsible for converting user entity
 * to various user-related DTOs and updating from form objects.
 * <p>
 * This mapper is used to handle user data transformations
 * between the domain layer and API layer.
 * </p>
 *
 * @author Brahim K
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    // Entity to Data Access Object

    /**
     * Maps the user to UserDTO
     *
     * @param user the user data
     * @return a User dto
     */
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "birthdate", source = "birthdate")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "isActive", source = "isActive")
    UserDTO toDto(User user);

    /**
     * Maps a user to userRoleDTO
     *
     * @param user the user data
     * @return a UserRoleDTO
     */
    @Mapping(target = "name", source = "name")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "pseudo", source = "pseudo")
    @Mapping(target = "role", source = "role")
    @Mapping(target = "createdAt", source = "createdAt")
    UserRoleDTO listRoleToDto(User user);

    /**
     * Maps a user to a UserPassworDTO
     *
     * @param user the user to convert
     * @return the user password DTO
     */
    @Mapping(target = "email", source = "email")
    UserPasswordDTO toUserPasswordDto(User user);

    // Form to Entity

    /**
     * Updates an existing User from a UserUpdateForm
     *
     * @param user the user entity to update
     * @param form the user form to update
     */
    void fromUpdateUserForm(UserUpdateForm form, @MappingTarget User user);

    /**
     * Change the password of the user
     * Update an existing User entity from a UserChangePasswordForm
     *
     * @param form the form containing the new password data
     * @param user the user entity to update
     * @return the updated User entity
     */
    User fromUserChangePasswordForm(UserChangePasswordForm form, @MappingTarget User user);

    /**
     * Map the form to entity User
     *
     * @param form the delete form
     * @param user the user entity to update
     */
    void fromDeleteForm(UserDeleteForm form, @MappingTarget User user);

}
