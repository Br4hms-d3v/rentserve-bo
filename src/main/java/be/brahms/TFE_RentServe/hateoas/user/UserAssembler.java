package be.brahms.TFE_RentServe.hateoas.user;

import be.brahms.TFE_RentServe.controller.UserController;
import be.brahms.TFE_RentServe.models.dtos.user.UserDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserAssembler implements RepresentationModelAssembler<UserDTO, EntityModel<UserDTO>> {

    /**
     * Default constructor for userAssembler
     */
    public UserAssembler() {
    }

    /**
     * Convert a UserDto to an EntityModel with HATEOAS links.
     * <p>
     * This method adds useful links to the UserDto,
     * like a link to the user itself and to the user list.
     *
     * @param user the user data to wrap
     * @return an EntityModel with the user data and HATEOAS links
     */
    @Override
    public EntityModel<UserDTO> toModel(UserDTO user) {
        return EntityModel.of(user, linkTo(methodOn(UserController.class).getUser(user.id())).withSelfRel());
    }
}
