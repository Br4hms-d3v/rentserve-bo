package be.brahms.TFE_RentServe.repositories;

import be.brahms.TFE_RentServe.models.entities.Review;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository for managing Review entity. Provide basic CRUD operation and mor using JpaRepository
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

  /**
   * Get a list of reviews only for userMaterial
   *
   * @return a list of reviews
   */
  @Query("SELECT r FROM Review r WHERE r.userMaterial.id IS NOT null")
  List<Review> listReviewByUserMaterial();
}
