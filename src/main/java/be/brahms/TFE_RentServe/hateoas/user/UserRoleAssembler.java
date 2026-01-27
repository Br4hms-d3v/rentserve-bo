package be.brahms.TFE_RentServe.hateoas.user;

import be.brahms.TFE_RentServe.controller.UserController;
import be.brahms.TFE_RentServe.models.dtos.user.UserRoleDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * UserRoleAssembler is a class that helps to convert UserRoleDto objects
 * into EntityModel objects. It creates models with links for user data.
 * <p>
 * This class uses a default constructor.
 */
@Component
public class UserRoleAssembler implements RepresentationModelAssembler<UserRoleDTO, EntityModel<UserRoleDTO>> {

    /**
     * Default constructor for UserRoleAssembler.
     */
    public UserRoleAssembler() {
    }

    /**
     * Convert a UserRoleDto to an EntityModel with HATEOAS links.
     * <p>
     * This method adds useful links to the UserRoleDto,
     * like a link to the user itself and to the user list.
     *
     * @param userRole the user data to wrap
     * @return an EntityModel with the user data and HATEOAS links
     */
    @Override
    public EntityModel<UserRoleDTO> toModel(UserRoleDTO userRole) {
        return EntityModel.of(userRole,
                linkTo(methodOn(UserController.class).getUsersByRole(userRole.role())).withRel("List of user by their role "));
    }

}
