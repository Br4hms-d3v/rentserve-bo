package be.brahms.TFE_RentServe.repositories;

import be.brahms.TFE_RentServe.models.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
