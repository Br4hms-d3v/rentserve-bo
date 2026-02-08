package be.brahms.TFE_RentServe.repositories;

import be.brahms.TFE_RentServe.models.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for managing Category entities.
 * Provides basic CRUD operations and more using JpaRepository.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsCategoryByNameCategory(String nameCategory);

    @Query("SELECT DISTINCT c FROM Category c WHERE c.nameCategory ILIKE %:nameCategory% ORDER BY c.nameCategory ASC")
    List<Category> searchCategory(@Param("nameCategory") String nameCategory);

}
