package be.brahms.TFE_RentServe.mappers;

import be.brahms.TFE_RentServe.models.dtos.favor.FavorNameDTO;
import be.brahms.TFE_RentServe.models.dtos.user.UserPseudoDTO;
import be.brahms.TFE_RentServe.models.dtos.userFavor.UserFavorByIdDTO;
import be.brahms.TFE_RentServe.models.dtos.userFavor.UserFavorDTO;
import be.brahms.TFE_RentServe.models.entities.Picture;
import be.brahms.TFE_RentServe.models.entities.UserFavor;
import be.brahms.TFE_RentServe.models.forms.userFavor.UpdateUserFavorForm;
import be.brahms.TFE_RentServe.models.forms.userFavor.UserFavorCreateForm;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper responsible for converting user favor entity to various userFavor-related DTOs and
 * updating form objects
 *
 * <p>This mapper is used to handle user favor data transformations between the domain layer and API
 * layer
 *
 * @author Brahim K
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserFavorMapper {

  // Entity to DTO

  /**
   * Convert a user favor to a UserFavorDTO Take only first picture to display
   *
   * @param userFavor the user favor entity
   * @return a user favor DTO
   */
  default UserFavorDTO toListDto(UserFavor userFavor) {
    String firstPicture =
        userFavor.getPictures().stream()
            .map(Picture::getNamePicture)
            .findFirst()
            .orElse("imageByDefault.png");

    return new UserFavorDTO(
        userFavor.getId(), userFavor.getPriceHourFavor(), userFavor.isAvailable(), firstPicture);
  }

  /**
   * Convert a UserFavor to an UserFavorDto
   *
   * @param userFavor the user Favor entity
   * @return the userFavor entity
   */
  UserFavorDTO toDto(UserFavor userFavor);

  /**
   * Convert a user favor to a UserFavorByIdDTO Get more details : - description about the favor -
   * list of pictures - the price per hour - the owner user - the name of favor
   *
   * @param userFavor the user favor entity
   * @return a user favor details
   */
  default UserFavorByIdDTO toIdDto(UserFavor userFavor) {
    List<String> pictures = userFavor.getPictures().stream().map(Picture::getNamePicture).toList();

    // Get the pseudo from user
    UserPseudoDTO userPseudo = new UserPseudoDTO(userFavor.getUser().getPseudo());

    // Get the name from favor
    FavorNameDTO favorName = new FavorNameDTO(userFavor.getFavor().getNameFavor());

    return new UserFavorByIdDTO(
        userFavor.getId(),
        userFavor.getDescriptionFavor(),
        userFavor.getPriceHourFavor(),
        userFavor.isAvailable(),
        pictures,
        userPseudo,
        favorName);
  }

  //  Form to Entity

  /**
   * Convert a UserFavorForm to a UserFavor entity. Used when creating a new user favor
   *
   * @param form the user favor form
   * @return the new user favor
   */
  UserFavor fromUserFavorForm(UserFavorCreateForm form);

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
   * Convert a UserFavorForm to a UserFavor entity. Used when updated a user favor
   *
   * @param form the user favor update form
   * @param userFavor the user favor entity
   */
  void fromUpdateUserFavorForm(UpdateUserFavorForm form, @MappingTarget UserFavor userFavor);
}
