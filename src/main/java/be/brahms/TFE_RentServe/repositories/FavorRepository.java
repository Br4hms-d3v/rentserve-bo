package be.brahms.TFE_RentServe.repositories;

import be.brahms.TFE_RentServe.models.entities.Favor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for managing Favor  entities.
 * Provides basic CRUD operations and more
 */
@Repository
public interface FavorRepository extends JpaRepository<Favor, Long> {

    /**
     * The method check if the name of service exist
     * True if the name of favor exist
     * False if then name of favor doesn't exist
     *
     * @param nameFavor The name of favor
     * @return a boolean
     */
    Boolean existsFavorByNameFavor(String nameFavor);

    /**
     * Make a list of favour only by group on category name
     *
     * @param categoryName the name of category
     * @return a list of favour from the same name of category
     */
    @Query("SELECT f FROM Favor f JOIN f.category c WHERE c.nameCategory = :categoryName")
    List<Favor> findFavorsByCategoryName(@Param("categoryName") String categoryName);
}
