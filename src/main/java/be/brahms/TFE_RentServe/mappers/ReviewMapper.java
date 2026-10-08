package be.brahms.TFE_RentServe.mappers;

import be.brahms.TFE_RentServe.models.dtos.review.ReviewByIdDTO;
import be.brahms.TFE_RentServe.models.dtos.review.ReviewDTO;
import be.brahms.TFE_RentServe.models.dtos.review.ReviewUserFavorDTO;
import be.brahms.TFE_RentServe.models.dtos.review.ReviewUserMaterialDTO;
import be.brahms.TFE_RentServe.models.dtos.user.UserPseudoDTO;
import be.brahms.TFE_RentServe.models.dtos.userFavor.UserFavorNameDTO;
import be.brahms.TFE_RentServe.models.dtos.userMaterial.UserMaterialNameDTO;
import be.brahms.TFE_RentServe.models.entities.Review;
import be.brahms.TFE_RentServe.models.forms.review.ReviewUpdateForm;
import be.brahms.TFE_RentServe.models.forms.review.ReviewUserFavorForm;
import be.brahms.TFE_RentServe.models.forms.review.ReviewUserMaterialForm;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper responsible for converting review entity to various review-related DTOs and updating from
 * form objects.
 *
 * <p>This mapper is used to handle review data transformations between the domain layer and API
 * layer.
 *
 * @author Brahim K
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReviewMapper {

  // Entity to DTO

  /**
   * Converts a Review entity into a ReviewUserMaterialDTO.
   *
   * @param review the review to convert
   * @return the review DTO with user and material information
   */
  default ReviewUserMaterialDTO toListDto(Review review) {
    UserMaterialNameDTO userMaterial = null;

    // UserMaterial
    if (review.getUserMaterial() != null
        && review.getUserMaterial().getMaterial() != null
        && review.getUserMaterial().getMaterial().getNameMaterial() != null) {

      userMaterial =
          new UserMaterialNameDTO(review.getUserMaterial().getMaterial().getNameMaterial());
    }

    // This is the same methode from userMaterialName but the code is short
    UserPseudoDTO userPseudo =
        review.getUser() != null ? new UserPseudoDTO(review.getUser().getPseudo()) : null;

    return new ReviewUserMaterialDTO(
        review.getId(),
        review.getComment(),
        review.getRating(),
        review.getIsActive(),
        userPseudo,
        userMaterial,
        review.getCreatedAt(),
        review.getUpdatedAt());
  }

  /**
   * Converts a Review entity into a ReviewUserFavorDTO.*
   *
   * @param review the review to convert
   * @return the review DTO with user and favor information
   */
  default ReviewUserFavorDTO toListDtoUF(Review review) {
    UserFavorNameDTO userFavor = null;

    // UserMaterial
    if (review.getUserFavor() != null
        && review.getUserFavor().getFavor() != null
        && review.getUserFavor().getFavor().getNameFavor() != null) {

      userFavor = new UserFavorNameDTO(review.getUserFavor().getFavor().getNameFavor());
    }

    // This is the same methode from userMaterialName but the code is short
    UserPseudoDTO userPseudo =
        review.getUser() != null ? new UserPseudoDTO(review.getUser().getPseudo()) : null;

    return new ReviewUserFavorDTO(
        review.getId(),
        review.getComment(),
        review.getRating(),
        review.getIsActive(),
        userPseudo,
        userFavor,
        review.getCreatedAt(),
        review.getUpdatedAt());
  }

  /**
   * Convert a review entity to a reviewByIdDTO. This DTO contains only the id of the review.
   *
   * @param review the review entity
   * @return the reviewByIdDTO
   */
  default ReviewByIdDTO toIdDto(Review review) {

    // UserMaterial
    UserMaterialNameDTO userMaterial =
        review.getUserMaterial() != null
            ? new UserMaterialNameDTO(review.getUserMaterial().getMaterial().getNameMaterial())
            : null;

    // UserFavor
    UserFavorNameDTO userFavor =
        review.getUserFavor() != null
            ? new UserFavorNameDTO(review.getUserFavor().getFavor().getNameFavor())
            : null;

    // UserPseudo
    UserPseudoDTO userPseudo =
        review.getUser() != null ? new UserPseudoDTO(review.getUser().getPseudo()) : null;

    return new ReviewByIdDTO(
        review.getId(),
        review.getComment(),
        review.getRating(),
        review.getIsActive(),
        userPseudo,
        userMaterial,
        userFavor,
        review.getCreatedAt(),
        review.getUpdatedAt());
  }

  /**
   * Convert a review entity to a reviewDTO. This DTO contains the edited review.
   *
   * @param review the review entity
   * @return the reviewByIdDTO
   */
  default ReviewDTO toDto(Review review) {

    return new ReviewDTO(
        review.getId(), review.getComment(), review.getRating(), review.getIsActive());
  }

  // Form to Entity

  /**
   * Convert a ReviewForm to a Review entity. Used when creating a new review
   *
   * @param form the review form
   * @return the review entity
   */
  Review fromReviewUMForm(ReviewUserMaterialForm form);

  /**
   * Convert a ReviewForm to a Review entity. Used when creating a new review
   *
   * @param form the review form
   * @return the review entity
   */
  Review fromReviewUFForm(ReviewUserFavorForm form);

  /**
   * Convert a ReviewForm to a Review entity. Used when update review
   *
   * @param form the review form
   * @param review the identifier review
   * @return the review entity
   */
  Review fromReviewUpdateForm(ReviewUpdateForm form, @MappingTarget Review review);
}
