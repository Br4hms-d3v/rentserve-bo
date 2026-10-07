package be.brahms.TFE_RentServe.services.impl;

import be.brahms.TFE_RentServe.exceptions.review.ReviewException;
import be.brahms.TFE_RentServe.exceptions.user.UserNotFoundException;
import be.brahms.TFE_RentServe.mappers.ReviewMapper;
import be.brahms.TFE_RentServe.models.dtos.review.ReviewByIdDTO;
import be.brahms.TFE_RentServe.models.dtos.review.ReviewUserFavorDTO;
import be.brahms.TFE_RentServe.models.dtos.review.ReviewUserMaterialDTO;
import be.brahms.TFE_RentServe.models.entities.Review;
import be.brahms.TFE_RentServe.repositories.ReviewRepository;
import be.brahms.TFE_RentServe.repositories.UserRepository;
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
  private final UserRepository userRepository;

  /**
   * Constructor with parameters
   *
   * @param reviewRepository the reviewRepo to access review data
   * @param reviewMapper the reviewMapper
   * @param userRepository the userRepo to access user data
   */
  @Autowired
  public ReviewServiceImpl(
      ReviewRepository reviewRepository, ReviewMapper reviewMapper, UserRepository userRepository) {
    this.reviewRepository = reviewRepository;
    this.reviewMapper = reviewMapper;
    this.userRepository = userRepository;
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
   *
   * @param id the identifier review
   * @return a review by his ID
   */
  public ReviewByIdDTO findReviewById(Long id) {
    Review review = reviewRepository.findById(id).orElseThrow();

    return reviewMapper.toIdDto(review);
  }

  /**
   * Find all reviews by user ID
   *
   * @param id the identifier user
   * @return a list of review from user
   */
  @Override
  public List<ReviewByIdDTO> findReviewByUserId(Long id) {

    List<Review> listReviewUser = reviewRepository.findReviewByUserId(id);

    userRepository.findById(id).orElseThrow(UserNotFoundException::new);

    if (listReviewUser.isEmpty()) {
      throw new ReviewException("La list est vide");
    }
    return listReviewUser.stream().map(reviewMapper::toIdDto).toList();
  }
}
