package be.brahms.TFE_RentServe.services;

import be.brahms.TFE_RentServe.models.dtos.review.ReviewByIdDTO;
import be.brahms.TFE_RentServe.models.dtos.review.ReviewDTO;
import be.brahms.TFE_RentServe.models.dtos.review.ReviewUserFavorDTO;
import be.brahms.TFE_RentServe.models.dtos.review.ReviewUserMaterialDTO;
import be.brahms.TFE_RentServe.models.forms.review.ReviewUpdateForm;
import be.brahms.TFE_RentServe.models.forms.review.ReviewUserFavorForm;
import be.brahms.TFE_RentServe.models.forms.review.ReviewUserMaterialForm;
import jakarta.validation.Valid;
import java.util.List;

/**
 * Service interface for managing review. Defines business operations related to review entities.
 */
public interface ReviewService {

  /**
   * This method get a list of all review from all userMaterials
   *
   * @return a list of review from user material
   */
  List<ReviewUserMaterialDTO> findReviewsUserMaterial();

  /**
   * This method get a list of all review from all userFavour
   *
   * @return a list of review from user favor
   */
  List<ReviewUserFavorDTO> findReviewsUserFavor();

  /**
   * This method get a review by id
   *
   * @param id the identifier review
   * @return a review
   */
  ReviewByIdDTO findReviewById(Long id);

  /**
   * This method get a list of reviews from user
   *
   * @param id the identifier user
   * @return a list of review (by user id)
   */
  List<ReviewByIdDTO> findReviewByUserId(Long id);

  /**
   * This method get a list of reviews from user material
   *
   * @param id the identifier of user material
   * @return a list of reviews from user material id
   */
  List<ReviewUserMaterialDTO> findReviewsUserMaterialById(long id);

  /**
   * This method get a list of reviews from user favor
   *
   * @param id the identifier of user favor
   * @return a list of reviews from user favor id
   */
  List<ReviewUserFavorDTO> findReviewsUserFavorById(long id);

  /**
   * This method create a review for user material
   *
   * @param form the form to create a new review for user material
   * @return a review
   */
  ReviewUserMaterialDTO createReviewUserMaterial(@Valid ReviewUserMaterialForm form);

  /**
   * This method create a review for user favor
   *
   * @param form the form to create a new review for user favor
   * @return a review
   */
  ReviewUserFavorDTO createReviewUserFavor(@Valid ReviewUserFavorForm form);

  /**
   * This method update a review for review
   *
   * @param form the form to update a review
   * @param id the identifier review
   * @return a review edited
   */
  ReviewDTO updateReview(long id, @Valid ReviewUpdateForm form);
}
