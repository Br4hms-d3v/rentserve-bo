package be.brahms.TFE_RentServe.controller;

import be.brahms.TFE_RentServe.hateoas.review.ReviewAssembler;
import be.brahms.TFE_RentServe.models.dtos.review.ReviewByIdDTO;
import be.brahms.TFE_RentServe.models.dtos.review.ReviewUserFavorDTO;
import be.brahms.TFE_RentServe.models.dtos.review.ReviewUserMaterialDTO;
import be.brahms.TFE_RentServe.services.ReviewService;
import java.util.List;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller manages Review It has a method to display - a list of reviews only for user
 * material - a list of reviews only for user favor
 *
 * @author Brahim K
 */
@RestController
@RequestMapping("/api/review/")
public class ReviewController {

  private final ReviewService reviewService;
  private final ReviewAssembler reviewAssembler;

  /**
   * This constructor is used to inject the necessary service for handling review relating request
   *
   * @param reviewService the service used for review management
   * @param reviewAssembler the assembler used to get a link on Hateoas
   */
  public ReviewController(ReviewService reviewService, ReviewAssembler reviewAssembler) {
    this.reviewService = reviewService;
    this.reviewAssembler = reviewAssembler;
  }

  /**
   * Get a list of reviews only for userMaterial
   *
   * @return a list of reviews
   */
  @GetMapping("list-material")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<CollectionModel<ReviewUserMaterialDTO>> getAllReviewsFromUserMaterial() {
    List<ReviewUserMaterialDTO> reviewList = reviewService.findReviewsUserMaterial();
    CollectionModel<ReviewUserMaterialDTO> reviewModel =
        reviewAssembler.toCollectionModel(reviewList);

    return ResponseEntity.ok().body(reviewModel);
  }

  /**
   * Get a list of reviews only for userFavor
   *
   * @return a list of reviews
   */
  @GetMapping("list-favor")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<CollectionModel<ReviewUserFavorDTO>> getAllReviewsFromUserFavor() {
    List<ReviewUserFavorDTO> reviewList = reviewService.findReviewsUserFavor();
    CollectionModel<ReviewUserFavorDTO> reviewModel =
        reviewAssembler.toCollectionModelUF(reviewList);

    return ResponseEntity.ok().body(reviewModel);
  }

  /**
   * Get review by id
   *
   * @return a review by his id
   */
  @GetMapping("{id}")
  @PreAuthorize("hasAnyRole('MEMBER', 'MODERATOR', 'ADMIN')")
  public ResponseEntity<EntityModel<ReviewByIdDTO>> getReviewsById(@PathVariable long id) {
    ReviewByIdDTO review = reviewService.findReviewById(id);
    EntityModel<ReviewByIdDTO> reviewModel =
            reviewAssembler.toIdModel(review);

    return ResponseEntity.ok().body(reviewModel);
  }


}
