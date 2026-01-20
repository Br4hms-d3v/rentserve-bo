package be.brahms.TFE_RentServe.mappers;

import be.brahms.TFE_RentServe.models.dtos.user.UserTokenDTO;
import be.brahms.TFE_RentServe.models.entities.User;
import be.brahms.TFE_RentServe.models.forms.user.UserForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for auth data.
 * It converts User and UserForm objects
 */
@Mapper(componentModel = "spring")
public interface AuthMapper {
    /**
     * Create a UserTokenDTO from a User and a token.
     *
     * @param user  the user data
     * @param token the token value
     * @return a UserTokenDTO
     */

    UserTokenDTO toTokenDTO(User user, String token);

    /**
     * Create a User from a UserForm.
     * Some fields are ignored.
     * Role is always MEMBER.
     * isActive is always false.
     *
     * @param form the user form
     * @return a new User
     */

    User fromUserForm(UserForm form);
}
