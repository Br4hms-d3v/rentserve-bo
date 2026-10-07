package be.brahms.TFE_RentServe.hateoas.review;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import be.brahms.TFE_RentServe.controller.ReviewController;
import be.brahms.TFE_RentServe.models.dtos.review.ReviewByIdDTO;
import be.brahms.TFE_RentServe.models.dtos.review.ReviewUserFavorDTO;
import be.brahms.TFE_RentServe.models.dtos.review.ReviewUserMaterialDTO;
import java.util.List;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

/**
 * ReviewAssembler is a class that helps to convert ReviewDTO objects into EntityModel objects. It
 * creates models with links for review data.
 */
@Component
public class ReviewAssembler
    implements RepresentationModelAssembler<
        ReviewUserMaterialDTO, EntityModel<ReviewUserMaterialDTO>> {

  /** Constructor by default for ReviewAssembler */
  public ReviewAssembler() {}

  /**
   * Convert a ReviewDto to an EntityModel with HATEOAS links. These methods add links to the
   * ReviewDto, a link for Review : - Get a list review for user materials
   *
   * @param review the review data to wrap
   * @return an EntityModel with the Review data and HATEOAS links
   */
  @Override
  public EntityModel<ReviewUserMaterialDTO> toModel(ReviewUserMaterialDTO review) {
    return null;
  }

  /**
   * Creates a collection of review DTOs with a link to the list of reviews.
   *
   * @param review the list of reviews
   * @return a collection of reviews with a link
   */
  public CollectionModel<ReviewUserMaterialDTO> toCollectionModel(
      List<ReviewUserMaterialDTO> review) {
    return CollectionModel.of(
        review,
        linkTo(methodOn(ReviewController.class).getAllReviewsFromUserMaterial())
            .withRel("List of reviews from user material"));
  }

  /**
   * Creates a collection of review DTOs with a link to the list of reviews from user material ID.
   *
   * @param review the list of reviews from user material ID
   * @param id the identifier user material
   * @return a collection of reviews with a link
   */
  public CollectionModel<ReviewUserMaterialDTO> toCollectionModelID(
      List<ReviewUserMaterialDTO> review, long id) {
    return CollectionModel.of(
        review,
        linkTo(methodOn(ReviewController.class).getAllReviewsFromUserMaterial())
            .withRel("List of reviews from user material"),
        linkTo(methodOn(ReviewController.class).getReviewsByUserMaterialId(id))
            .withRel("List review from user material ID"));
  }

  /**
   * Creates a collection of review DTOs with a link to the list of reviews.
   *
   * @param review the list of reviews for user favor
   * @return a collection of reviews with a link
   */
  public CollectionModel<ReviewUserFavorDTO> toCollectionModelUF(List<ReviewUserFavorDTO> review) {
    return CollectionModel.of(
        review,
        linkTo(methodOn(ReviewController.class).getAllReviewsFromUserFavor())
            .withRel("List of reviews from user favor"));
  }

  /**
   * Convert a ReviewByIdDto to an EntityModel with HATEOAS links.
   *
   * <p>This method adds useful links to the ReviewByIdDto, a link for review by id
   *
   * @param review the review data to wrap
   * @return an EntityModel with the review details and HATEOAS links
   */
  public EntityModel<ReviewByIdDTO> toIdModel(ReviewByIdDTO review) {
    return EntityModel.of(
        review,
        linkTo(methodOn(ReviewController.class).getReviewsById(review.id()))
            .withRel("Get review by ID"));
  }

  /**
   * Convert a ReviewByIdDto to a CollectionModel with HATEOAS links.
   *
   * <p>This method adds useful links to the ReviewByIdDto, a link for review by id
   *
   * @param review the review data to wrap
   * @param userId the identifier from user
   * @return a CollectionModel with a list the reviews from user and HATEOAS links
   */
  public CollectionModel<ReviewByIdDTO> toListModel(List<ReviewByIdDTO> review, long userId) {
    return CollectionModel.of(
        review,
        linkTo(methodOn(ReviewController.class).getReviewsByUserId(userId))
            .withRel("Get review by user ID"));
  }
}
