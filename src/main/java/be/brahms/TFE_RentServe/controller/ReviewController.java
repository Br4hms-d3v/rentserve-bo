package be.brahms.TFE_RentServe.controller;

import be.brahms.TFE_RentServe.hateoas.review.ReviewAssembler;
import be.brahms.TFE_RentServe.models.dtos.review.ReviewByIdDTO;
import be.brahms.TFE_RentServe.models.dtos.review.ReviewDTO;
import be.brahms.TFE_RentServe.models.dtos.review.ReviewUserFavorDTO;
import be.brahms.TFE_RentServe.models.dtos.review.ReviewUserMaterialDTO;
import be.brahms.TFE_RentServe.models.forms.review.ReviewUpdateForm;
import be.brahms.TFE_RentServe.models.forms.review.ReviewUserFavorForm;
import be.brahms.TFE_RentServe.models.forms.review.ReviewUserMaterialForm;
import be.brahms.TFE_RentServe.services.ReviewService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
   * @param id the identifier review
   * @return a review by his id
   */
  @GetMapping("{id}")
  @PreAuthorize("hasAnyRole('MEMBER', 'MODERATOR', 'ADMIN')")
  public ResponseEntity<EntityModel<ReviewByIdDTO>> getReviewsById(@PathVariable long id) {
    ReviewByIdDTO review = reviewService.findReviewById(id);
    EntityModel<ReviewByIdDTO> reviewModel = reviewAssembler.toIdModel(review);

    return ResponseEntity.ok().body(reviewModel);
  }

  /**
   * Get review from userID
   *
   * @param id the identifier of user
   * @return a list of reviews from user id
   */
  @GetMapping("user/{id}")
  @PreAuthorize("hasAnyRole('MEMBER', 'MODERATOR', 'ADMIN')")
  public ResponseEntity<CollectionModel<ReviewByIdDTO>> getReviewsByUserId(@PathVariable long id) {
    List<ReviewByIdDTO> listReviewByUser = reviewService.findReviewByUserId(id);
    CollectionModel<ReviewByIdDTO> reviewModel = reviewAssembler.toListModel(listReviewByUser, id);

    return ResponseEntity.ok().body(reviewModel);
  }

  /**
   * Get a list review from user material ID
   *
   * @param id the identifier of user material
   * @return a list of reviews
   */
  @GetMapping("user-material/{id}")
  @PreAuthorize("hasAnyRole('MEMBER', 'MODERATOR', 'ADMIN')")
  public ResponseEntity<CollectionModel<ReviewUserMaterialDTO>> getReviewsByUserMaterialId(
      @PathVariable long id) {
    List<ReviewUserMaterialDTO> listReviewUserMaterialID =
        reviewService.findReviewsUserMaterialById(id);
    CollectionModel<ReviewUserMaterialDTO> reviewsModel =
        reviewAssembler.toCollectionModelID(listReviewUserMaterialID, id);
    return ResponseEntity.ok().body(reviewsModel);
  }

  /**
   * Get a list review from user favor ID
   *
   * @param id the identifier of user favor
   * @return a list of reviews
   */
  @GetMapping("user-favor/{id}")
  @PreAuthorize("hasAnyRole('MEMBER', 'MODERATOR', 'ADMIN')")
  public ResponseEntity<CollectionModel<ReviewUserFavorDTO>> getReviewsByUserFavorId(
      @PathVariable long id) {
    List<ReviewUserFavorDTO> listReviewUserFavorID = reviewService.findReviewsUserFavorById(id);
    CollectionModel<ReviewUserFavorDTO> reviewsModel =
        reviewAssembler.toCollectionModelUFID(listReviewUserFavorID, id);
    return ResponseEntity.ok().body(reviewsModel);
  }

  /**
   * Write a review for user material
   *
   * @param form the form to write a review for user material
   * @return a reviews with link
   */
  @PostMapping("material")
  @PreAuthorize("hasAnyRole('MEMBER', 'MODERATOR', 'ADMIN')")
  public ResponseEntity<EntityModel<ReviewUserMaterialDTO>> createReviewsUserMaterial(
      @RequestBody @Valid ReviewUserMaterialForm form) {
    ReviewUserMaterialDTO newReview = reviewService.createReviewUserMaterial(form);
    return ResponseEntity.ok().body(reviewAssembler.toModel(newReview));
  }

  /**
   * Write a review for user favor
   *
   * @param form the form to write a review for user favor
   * @return a reviews favor with link
   */
  @PostMapping("favor")
  @PreAuthorize("hasAnyRole('MEMBER', 'MODERATOR', 'ADMIN')")
  public ResponseEntity<EntityModel<ReviewUserFavorDTO>> createReviewsUserFavor(
      @RequestBody @Valid ReviewUserFavorForm form) {
    ReviewUserFavorDTO newReview = reviewService.createReviewUserFavor(form);
    return ResponseEntity.ok().body(reviewAssembler.toModelUF(newReview));
  }

  /**
   * Edit the review by id
   *
   * @param form the form to edit review
   * @param id the identifier
   * @return review edited
   */
  @PutMapping("edit/{id}")
  @PreAuthorize("hasAnyRole('MEMBER', 'MODERATOR', 'ADMIN')")
  public ResponseEntity<EntityModel<ReviewDTO>> updateReview(
      @PathVariable long id, @RequestBody @Valid ReviewUpdateForm form) {
    ReviewDTO editReview = reviewService.updateReview(id, form);
    return ResponseEntity.ok().body(reviewAssembler.toModel(editReview));
  }

  /**
   * Delete the review
   *
   * @param id the identifier
   * @return a message to confirm has been deleting
   */
  @DeleteMapping("delete/{id}")
  @PreAuthorize("hasAnyRole('MEMBER', 'MODERATOR', 'ADMIN')")
  public ResponseEntity<String> deleteReview(@PathVariable long id) {
    reviewService.deleteReview(id);
    return ResponseEntity.ok().body("Le commentaire a été supprimée avec succès.");
  }
}
