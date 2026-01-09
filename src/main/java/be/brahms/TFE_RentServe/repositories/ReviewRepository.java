package be.brahms.TFE_RentServe.repositories;

import be.brahms.TFE_RentServe.models.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
