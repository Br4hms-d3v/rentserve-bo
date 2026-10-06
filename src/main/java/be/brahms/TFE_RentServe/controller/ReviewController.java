package be.brahms.TFE_RentServe.controller;

import be.brahms.TFE_RentServe.hateoas.review.ReviewAssembler;
import be.brahms.TFE_RentServe.models.dtos.review.ReviewUserMaterialDTO;
import be.brahms.TFE_RentServe.services.ReviewService;
import java.util.List;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller manages Review It has a method to display - a list of reviews only for user
 * material
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
  //    @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<CollectionModel<ReviewUserMaterialDTO>> getAllReviewsFromUserMaterial() {
    List<ReviewUserMaterialDTO> reviewList = reviewService.findReviewsUserMaterial();
    CollectionModel<ReviewUserMaterialDTO> reviewModel =
        reviewAssembler.toCollectionModel(reviewList);

    return ResponseEntity.ok().body(reviewModel);
  }
}
