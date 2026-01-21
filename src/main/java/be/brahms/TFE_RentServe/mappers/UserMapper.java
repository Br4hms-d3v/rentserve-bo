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

/**
 * Mapper for user data
 * It converts User
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    // Entity to Data Access Object

    /**
     * Map the UserDTO from a User
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
     * Map a UserRoleDto from a user
     *
     * @param user the user data
     * @return a User from role
     */
    @Mapping(target = "name", source = "name")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "pseudo", source = "pseudo")
    @Mapping(target = "role", source = "role")
    @Mapping(target = "createdAt", source = "createdAt")
    UserRoleDTO listRoleToDto(User user);

    @Mapping(target = "email", source = "email")
    UserPasswordDTO toUserPasswordDto(User user);

    // Form to Entity

    /**
     * Update a user from userUpdateForm
     * Some fields are ignored.
     *
     * @param form the user form to update
     */
    void fromUpdateUserForm(UserUpdateForm form, @MappingTarget User user);

    User fromUserChangePasswordForm(UserChangePasswordForm form, @MappingTarget User user);

    /**
     * Map the form to entity User
     *
     * @param form the form to delete
     * @param user the data user
     */
    void fromDeleteForm(UserDeleteForm form, @MappingTarget User user);

}
