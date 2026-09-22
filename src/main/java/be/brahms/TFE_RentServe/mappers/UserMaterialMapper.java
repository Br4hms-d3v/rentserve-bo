package be.brahms.TFE_RentServe.mappers;

import be.brahms.TFE_RentServe.models.dtos.material.MaterialNameDTO;
import be.brahms.TFE_RentServe.models.dtos.user.UserPseudoDTO;
import be.brahms.TFE_RentServe.models.dtos.userMaterial.UserMaterialByIdDTO;
import be.brahms.TFE_RentServe.models.dtos.userMaterial.UserMaterialDTO;
import be.brahms.TFE_RentServe.models.entities.Picture;
import be.brahms.TFE_RentServe.models.entities.UserMaterial;
import be.brahms.TFE_RentServe.models.forms.userMaterial.UserMaterialCreateForm;
import be.brahms.TFE_RentServe.models.forms.userMaterial.UserMaterialUpdateForm;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper responsible for converting user material entity to various userMaterial-related DTOs and
 * updating form objects
 *
 * <p>This mapper is used to handle user material data transformations between the domain layer and
 * API layer
 *
 * @author Brahim K
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMaterialMapper {
  // Entity to DTO

  /**
   * Convert a user material UserMaterialDTO Take only first picture to display
   *
   * @param userMaterial the user material entity
   * @return a user material DTO
   */
  default UserMaterialDTO toListDto(UserMaterial userMaterial) {
    String firstPicture =
        userMaterial.getPictures().stream()
            .map(Picture::getNamePicture)
            .findFirst()
            .orElse("imageByDefault.png");

    // Get the name from material
    MaterialNameDTO nameMaterial =
        new MaterialNameDTO(userMaterial.getMaterial().getNameMaterial());

    return new UserMaterialDTO(
        userMaterial.getId(),
        nameMaterial,
        userMaterial.getPriceHourMaterial(),
        userMaterial.isAvailable(),
        firstPicture);
  }

  /**
   * Convert a UserMaterial to an UserMaterialDto
   *
   * @param userMaterial the user material entity
   * @return the userMaterial dto
   */
  UserMaterialDTO toDto(UserMaterial userMaterial);

  /**
   * Convert a user material to a UserMaterialByIdDTO Get more details : - description about the
   * material - list of pictures - the price per hour - the owner user - the name of material
   *
   * @param userMaterial the user material entity
   * @return a user material details
   */
  default UserMaterialByIdDTO toIdDto(UserMaterial userMaterial) {
    List<String> pictures =
            userMaterial.getPictures() == null
                    ? List.of()
                    : userMaterial.getPictures()
                    .stream()
                    .map(Picture::getNamePicture)
                    .toList();

    UserPseudoDTO userPseudo =
            new UserPseudoDTO(userMaterial.getUser().getPseudo());

    MaterialNameDTO nameMaterial =
            new MaterialNameDTO(userMaterial.getMaterial().getNameMaterial());

    return new UserMaterialByIdDTO(
            userMaterial.getId(),
            userMaterial.getDescriptionMaterial(),
            userMaterial.getPriceHourMaterial(),
            userMaterial.isAvailable(),
            pictures,
            userPseudo,
            nameMaterial,
            userMaterial.getStateMaterial()
    );
  }

  // Form to Entity

  /**
   * Convert a UserMaterialForm to a UserMaterial entity. Used when creating a new user material
   *
   * @param form the user material form
   * @return the new user material
   */
  UserMaterial fromUserMaterialForm(UserMaterialCreateForm form);

  /**
   * Create a Picture object from a picture name. If the name is null or empty, a default picture
   * name is used.
   *
   * @param namePicture the name of the picture
   * @return a picture object with a valid name
   */
  default Picture map(String namePicture) {
    Picture picture = new Picture();

    if (namePicture == null || namePicture.isBlank()) {
      picture.setNamePicture("imageByDefault.png");
    } else {
      picture.setNamePicture(namePicture);
    }

    return picture;
  }

  /**
   * Convert a UserMaterialForm to a UserMaterial entity. Used when updated a user material
   *
   * @param form the user material update form
   * @param userMaterial the user material entity
   */
  void fromUpdateUserMaterialForm(
      UserMaterialUpdateForm form, @MappingTarget UserMaterial userMaterial);
}
