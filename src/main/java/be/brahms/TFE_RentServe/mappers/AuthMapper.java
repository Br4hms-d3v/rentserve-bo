package be.brahms.TFE_RentServe.mappers;

import be.brahms.TFE_RentServe.models.dtos.user.UserTokenDTO;
import be.brahms.TFE_RentServe.models.entities.User;
import be.brahms.TFE_RentServe.models.forms.user.UserForm;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper responsible for authentication-related conversions.
 *
 * <p>This mapper handles transformations between user domain objects
 * and authentication-related DTOs.</p>
 *
 * @author Brahim K
 *
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthMapper {
    /**
     * Create a UserTokenDTO from a User and a token value.
     *
     * @param user  the user data
     * @param token the token value
     * @return a UserTokenDTO containing the user information and token
     */
    UserTokenDTO toTokenDTO(User user, String token);

    /**
     * Create a User from a UserForm.
     *
     * @param form the user form
     * @return a new User
     */
    User fromUserForm(UserForm form);
}
