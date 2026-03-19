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

    /**
     * Search a name of category
     * It's search only one name of category
     *
     * @param nameCategory the name of category
     * @return a category name
     */
    Category findCategoryByNameCategory(String nameCategory);

    /**
     * Search categories by name.
     * The search is not case-sensitive.
     * The result is ordered by category name (A to Z).
     *
     * @param nameCategory the text to search in category names
     * @return the list of matching categories
     */
    @Query("SELECT DISTINCT c FROM Category c WHERE c.nameCategory ILIKE %:nameCategory% ORDER BY c.nameCategory ASC")
    List<Category> searchCategory(@Param("nameCategory") String nameCategory);
}
