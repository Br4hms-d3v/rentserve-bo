package be.brahms.TFE_RentServe.repositories;

import be.brahms.TFE_RentServe.models.entities.Favor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for managing Favor  entities.
 * Provides basic CRUD operations and more
 */
@Repository
public interface FavorRepository extends JpaRepository<Favor, Long> {
}
