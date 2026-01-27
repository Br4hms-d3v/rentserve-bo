package be.brahms.TFE_RentServe.hateoas.user;

import be.brahms.TFE_RentServe.controller.UserController;
import be.brahms.TFE_RentServe.models.dtos.user.UserDTO;
import be.brahms.TFE_RentServe.models.dtos.user.UserPasswordDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * This assembler adds useful navigation links related to user
 * operations such as retrieving
 * updating
 * deleting a user
 * changing the user's password.
 */
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
     * <p>
     * The generated model contains links to:
     * <ul>
     * <li>Retrieve the user</li>
     * <li>Retrieve all users</li>
     * <li>Update the user</li>
     * <li>Change the user's password</li>
     * <li>Delete the user account</li>
     * </ul>
     *
     * @param user the user data to wrap
     * @return an EntityModel with the user data and HATEOAS links
     */
    @Override
    public EntityModel<UserDTO> toModel(UserDTO user) {
        return EntityModel.of(user,
                linkTo(methodOn(UserController.class).getUser(user.id())).withSelfRel(),
                linkTo(methodOn(UserController.class).getAllUsers()).withRel("List of all users"),
                linkTo(methodOn(UserController.class).putUserUpdate(user.id(), null)).withRel("Update the user: " + user.id()),
                linkTo(methodOn(UserController.class).changePassword(user.id(), null)).withRel("Change the password"),
                linkTo(methodOn(UserController.class).deleteAccount(user.id(), null)).withRel("Delete account")
        );
    }

    /**
     * Converts UserPasswordDTO into an entity
     * containing a self link to the password change operation.
     *
     * @param dto the password data transfer object
     * @return an EntityModel with a self HATEOAS link
     */
    public EntityModel<UserPasswordDTO> toModel1(UserPasswordDTO dto) {
        return EntityModel.of(
                dto,
                linkTo(methodOn(UserController.class).changePassword(dto.id(), null)).withSelfRel()
        );
    }

    /**
     * Create a HATEOAS to delete a user account
     *
     * @param id the identifier
     * @return a message to confirm the account deleted
     */
    public EntityModel<?> toModelDelete(long id) {
        return EntityModel.of(
                linkTo(methodOn(UserController.class).deleteAccount(id, null)).withRel("Delete account")
        );
    }


}
