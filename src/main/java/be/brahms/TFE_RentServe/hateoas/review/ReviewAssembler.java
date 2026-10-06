package be.brahms.TFE_RentServe.hateoas.review;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import be.brahms.TFE_RentServe.controller.ReviewController;
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
}
