package be.brahms.TFE_RentServe.services.impl;

import be.brahms.TFE_RentServe.exceptions.review.ReviewException;
import be.brahms.TFE_RentServe.exceptions.review.ReviewNotExistingException;
import be.brahms.TFE_RentServe.exceptions.user.UserNotFoundException;
import be.brahms.TFE_RentServe.exceptions.userFavor.UserFavorNotFoundException;
import be.brahms.TFE_RentServe.exceptions.userMaterial.UserMaterialNotFoundException;
import be.brahms.TFE_RentServe.mappers.ReviewMapper;
import be.brahms.TFE_RentServe.models.dtos.review.ReviewByIdDTO;
import be.brahms.TFE_RentServe.models.dtos.review.ReviewDTO;
import be.brahms.TFE_RentServe.models.dtos.review.ReviewUserFavorDTO;
import be.brahms.TFE_RentServe.models.dtos.review.ReviewUserMaterialDTO;
import be.brahms.TFE_RentServe.models.entities.Review;
import be.brahms.TFE_RentServe.models.entities.User;
import be.brahms.TFE_RentServe.models.entities.UserFavor;
import be.brahms.TFE_RentServe.models.entities.UserMaterial;
import be.brahms.TFE_RentServe.models.forms.review.ReviewUpdateForm;
import be.brahms.TFE_RentServe.models.forms.review.ReviewUserFavorForm;
import be.brahms.TFE_RentServe.models.forms.review.ReviewUserMaterialForm;
import be.brahms.TFE_RentServe.repositories.ReviewRepository;
import be.brahms.TFE_RentServe.repositories.UserFavorRepository;
import be.brahms.TFE_RentServe.repositories.UserMaterialRepository;
import be.brahms.TFE_RentServe.repositories.UserRepository;
import be.brahms.TFE_RentServe.services.ReviewService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service implementation for managing Review. Uses ReviewRepository to perform database operations
 * uses ReviewMapper to map between form to entity or dto to entity
 */
@Service
public class ReviewServiceImpl implements ReviewService {

  private final ReviewRepository reviewRepository;
  private final ReviewMapper reviewMapper;
  private final UserRepository userRepository;
  private final UserMaterialRepository userMaterialRepository;
  private final UserFavorRepository userFavorRepository;

  /**
   * Constructor with parameters
   *
   * @param reviewRepository the reviewRepo to access review data
   * @param reviewMapper the reviewMapper
   * @param userRepository the userRepo to access user data
   * @param userMaterialRepository the userMaterialRepo to access user material data
   * @param userFavorRepository the userFavorRepo to access user favor data
   */
  @Autowired
  public ReviewServiceImpl(
      ReviewRepository reviewRepository,
      ReviewMapper reviewMapper,
      UserRepository userRepository,
      UserMaterialRepository userMaterialRepository,
      UserFavorRepository userFavorRepository) {
    this.reviewRepository = reviewRepository;
    this.reviewMapper = reviewMapper;
    this.userRepository = userRepository;
    this.userMaterialRepository = userMaterialRepository;
    this.userFavorRepository = userFavorRepository;
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

  /**
   * Find all reviews by user material id
   *
   * @param id the identifier of user material
   * @return a list of reviews from user material ID
   */
  @Override
  public List<ReviewUserMaterialDTO> findReviewsUserMaterialById(long id) {
    List<Review> reviewsUserMaterialId = reviewRepository.findReviewByUserMaterialId(id);

    userMaterialRepository.findById(id).orElseThrow(UserMaterialNotFoundException::new);

    return reviewsUserMaterialId.stream().map(reviewMapper::toListDto).toList();
  }

  /**
   * Find all reviews by user favor id
   *
   * @param id the identifier of user favor
   * @return a list of reviews from user favor ID
   */
  @Override
  public List<ReviewUserFavorDTO> findReviewsUserFavorById(long id) {
    List<Review> reviewsUserFavorId = reviewRepository.findReviewByUserFavorId(id);

    userFavorRepository.findById(id).orElseThrow(UserFavorNotFoundException::new);

    return reviewsUserFavorId.stream().map(reviewMapper::toListDtoUF).toList();
  }

  /**
   * Write a review for user material Get user id by authenticate
   *
   * @param form the form to create a new review for user material
   * @return review for user material
   */
  @Override
  @Transactional
  public ReviewUserMaterialDTO createReviewUserMaterial(ReviewUserMaterialForm form) {
    Review newReview = reviewMapper.fromReviewUMForm(form);
    UserMaterial userMaterial =
        userMaterialRepository
            .findById(form.userMaterialID())
            .orElseThrow(UserMaterialNotFoundException::new);

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication != null && authentication.isAuthenticated()) {
      Object principal = authentication.getPrincipal();

      if (principal instanceof UserDetails userDetails) {
        String pseudo = userDetails.getUsername();

        User user = userRepository.findByPseudo(pseudo).orElseThrow(UserNotFoundException::new);
        newReview.setUser(user);
      }
    }

    newReview.setUserMaterial(userMaterial);

    reviewRepository.save(newReview);

    return reviewMapper.toListDto(newReview);
  }

  /**
   * Write a review for user material Get user id by authenticate
   *
   * @param form the form to create a new review for user favor
   * @return review user favor
   */
  @Override
  @Transactional
  public ReviewUserFavorDTO createReviewUserFavor(ReviewUserFavorForm form) {
    Review newReview = reviewMapper.fromReviewUFForm(form);
    UserFavor userFavor =
        userFavorRepository
            .findById(form.userFavorID())
            .orElseThrow(UserFavorNotFoundException::new);

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication != null && authentication.isAuthenticated()) {
      Object principal = authentication.getPrincipal();

      if (principal instanceof UserDetails userDetails) {
        String pseudo = userDetails.getUsername();

        User user = userRepository.findByPseudo(pseudo).orElseThrow(UserNotFoundException::new);
        newReview.setUser(user);
      }
    }

    newReview.setUserFavor(userFavor);

    reviewRepository.save(newReview);

    return reviewMapper.toListDtoUF(newReview);
  }

  /**
   * Edit a review for by id
   *
   * @param form the form to edit review
   * @return review edited
   */
  @Override
  @Transactional
  public ReviewDTO updateReview(long id, ReviewUpdateForm form) {
    Review reviewId = reviewRepository.findById(id).orElseThrow(ReviewNotExistingException::new);

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication != null && authentication.isAuthenticated()) {
      Object principal = authentication.getPrincipal();

      if (principal instanceof UserDetails userDetails) {
        String pseudo = userDetails.getUsername();

        User user = userRepository.findByPseudo(pseudo).orElseThrow(UserNotFoundException::new);
        reviewId.setUser(user);
      }
    }

    reviewId.setUpdatedAt(LocalDate.now());

    reviewMapper.fromReviewUpdateForm(form, reviewId);

    return reviewMapper.toDto(reviewRepository.save(reviewId));
  }
}
