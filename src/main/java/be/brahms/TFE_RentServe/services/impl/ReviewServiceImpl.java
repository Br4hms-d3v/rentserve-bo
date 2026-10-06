package be.brahms.TFE_RentServe.services.impl;

import be.brahms.TFE_RentServe.mappers.ReviewMapper;
import be.brahms.TFE_RentServe.models.dtos.review.ReviewByIdDTO;
import be.brahms.TFE_RentServe.models.dtos.review.ReviewUserFavorDTO;
import be.brahms.TFE_RentServe.models.dtos.review.ReviewUserMaterialDTO;
import be.brahms.TFE_RentServe.models.entities.Review;
import be.brahms.TFE_RentServe.repositories.ReviewRepository;
import be.brahms.TFE_RentServe.services.ReviewService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service implementation for managing Review. Uses ReviewRepository to perform database operations
 * uses ReviewMapper to map between form to entity or dto to entity
 */
@Service
public class ReviewServiceImpl implements ReviewService {

  private final ReviewRepository reviewRepository;
  private final ReviewMapper reviewMapper;

  /**
   * Constructor with parameters
   *
   * @param reviewRepository the reviewRepo to access review data
   * @param reviewMapper the reviewMapper
   */
  @Autowired
  public ReviewServiceImpl(ReviewRepository reviewRepository, ReviewMapper reviewMapper) {
    this.reviewRepository = reviewRepository;
    this.reviewMapper = reviewMapper;
  }

  /**
   * Finds all reviews for user materials.
   *
   * @return a list of review DTOs
   */
  @Override
  public List<ReviewUserMaterialDTO> findReviewsUserMaterial() {
    List<Review> listReviewUM = reviewRepository.listReviewByUserMaterial();

    if (listReviewUM.isEmpty()) {
      //            System.out.println("List reviews is empty ");
    }

    return listReviewUM.stream().map(reviewMapper::toListDto).toList();
  }

  /**
   * Finds all reviews for user favour.
   *
   * @return a list of review DTOs
   */
  @Override
  public List<ReviewUserFavorDTO> findReviewsUserFavor() {
    List<Review> listReviewUF = reviewRepository.listReviewByUserFavor();

    if (listReviewUF.isEmpty()) {
      //            System.out.println("List reviews is empty ");
    }

    return listReviewUF.stream().map(reviewMapper::toListDtoUF).toList();
  }

  /**
   * Find review by ID
   * @param id the identifier review
   * @return a review by his ID
   */
  public ReviewByIdDTO findReviewById(Long id) {
    Review review = reviewRepository.findById(id).orElseThrow();

    return reviewMapper.toIdDto(review);
  }
}
