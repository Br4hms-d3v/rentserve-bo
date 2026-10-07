package be.brahms.TFE_RentServe.services;

import be.brahms.TFE_RentServe.models.dtos.review.ReviewByIdDTO;
import be.brahms.TFE_RentServe.models.dtos.review.ReviewUserFavorDTO;
import be.brahms.TFE_RentServe.models.dtos.review.ReviewUserMaterialDTO;
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
}
