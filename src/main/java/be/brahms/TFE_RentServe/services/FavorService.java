package be.brahms.TFE_RentServe.services;

import be.brahms.TFE_RentServe.models.dtos.favor.FavorDTO;
import be.brahms.TFE_RentServe.models.forms.favor.FavorFormDTO;
import be.brahms.TFE_RentServe.models.forms.favor.UpdateFavorFormDTO;
import be.brahms.TFE_RentServe.models.entities.Favor;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Service interface for managing favor.
 * Defines business operations related to favor entity
 */
public interface FavorService {

    /**
     * This method get a list of favour
     *
     * @return list of favour
     */
    List<Favor> findAllFavour();

    /**
     * This method get a favor by ID
     *
     * @param id the identifier of favor
     * @return details about the favor
     */
    Favor findFavourById(Long id);

    /**
     * This method saves a new favor.
     *
     * @param form the form to create a new favor
     * @return the saved favor
     */
    Favor createFavor(FavorFormDTO form);

    /**
     * This method get a list of favour groups by his name of category
     *
     * @param categoryName the name of category
     * @return a list of favor grouped by name of category
     */
    List<Favor> findFavourByCategoryName(@Param("categoryName") String categoryName);

    /**
     * This method edit the favor
     *
     * @param id   the identifier of favor
     * @param form the form to edit the favor
     * @return the updated favor
     */
    FavorDTO updateFavor(Long id, UpdateFavorFormDTO form);

    /**
     * Soft delete favor
     *
     * @param id the identifier of favor
     */
    void deleteFavor(Long id);
}
