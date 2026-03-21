package be.brahms.TFE_RentServe.repositories;

import be.brahms.TFE_RentServe.models.entities.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for managing Material entity.
 * Provides basic CRUD and more
 */
@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
}
