package be.brahms.TFE_RentServe.hateoas.userMaterial;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import be.brahms.TFE_RentServe.controller.UserMaterialController;
import be.brahms.TFE_RentServe.models.dtos.userMaterial.UserMaterialByIdDTO;
import be.brahms.TFE_RentServe.models.dtos.userMaterial.UserMaterialDTO;
import java.util.List;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

/**
 * UserMaterialAssembler is a class that helps to convert UserMaterialDTO objects into EntityModel
 * objects. It creates models with links for userMaterial data.
 */
@Component
public class UserMaterialAssembler
    implements RepresentationModelAssembler<UserMaterialDTO, EntityModel<UserMaterialDTO>> {

  /** Constructor by default for CategoryAssembler */
  public UserMaterialAssembler() {}

  /**
   * Convert a UserMaterialDto to an EntityModel with HATEOAS links. These methods add links to the
   * UserMaterialDto, a link for UserMaterial : - Get a list of user Materials - Get a list of user
   * Materials group by Material ID - Get a details by id Material - Get a form to create a
   * userMaterial - Delete a user Material by ID
   *
   * @param userMaterial the user Material data to wrap
   * @return an EntityModel with the userMaterial data and HATEOAS links
   */
  @Override
  public EntityModel<UserMaterialDTO> toModel(UserMaterialDTO userMaterial) {
    return EntityModel.of(
        userMaterial,
        linkTo(methodOn(UserMaterialController.class).getAllUserMaterials())
            .withRel("List of users Materials"),
        linkTo(methodOn(UserMaterialController.class).getUserMaterialById(userMaterial.id()))
            .withRel("User material by ID"),
        linkTo(methodOn(UserMaterialController.class).createUserMaterial(null, null))
            .withRel("Create a new user material"),
        linkTo(methodOn(UserMaterialController.class).updateUserMaterial(userMaterial.id(), null))
            .withRel("Update user material"),
        linkTo(methodOn(UserMaterialController.class).deleteUserMaterial(userMaterial.id()))
            .withRel("Delete user material"));
  }

  /**
   * Convert a userMaterialDto to EntityModel with HATEOAS links. This method adds useful links to
   * the UserMaterial
   *
   * @param userMaterials the users Materials collection and HATEOAS links
   * @return a list of model with HATEOAS links
   */
  public CollectionModel<UserMaterialDTO> toCollectionModel(List<UserMaterialDTO> userMaterials) {
    return CollectionModel.of(
        userMaterials,
        linkTo(methodOn(UserMaterialController.class).getAllUserMaterials())
            .withRel("List of users Materials"));
  }

  /**
   * Convert a userMaterialByIdDto to EntityModel with HATEOAS links. This method adds useful links
   * to the UserMaterialID
   *
   * @param userMaterialByIdDTO the user Material entity model by ID and links
   * @return a list of model with HATEOAS links
   */
  public EntityModel<UserMaterialByIdDTO> toIdModel(UserMaterialByIdDTO userMaterialByIdDTO) {
    return EntityModel.of(
        userMaterialByIdDTO,
        linkTo(methodOn(UserMaterialController.class).getUserMaterialById(userMaterialByIdDTO.id()))
            .withRel("User material by ID"),
        linkTo(
                methodOn(UserMaterialController.class)
                    .updateUserMaterial(userMaterialByIdDTO.id(), null))
            .withRel("Update user material"),
        linkTo(methodOn(UserMaterialController.class).deleteUserMaterial(userMaterialByIdDTO.id()))
            .withRel("Delete user material"));
  }
}
