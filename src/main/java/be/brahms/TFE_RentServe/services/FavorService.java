package be.brahms.TFE_RentServe.services;

import be.brahms.TFE_RentServe.models.entities.Favor;

import java.util.List;

/**
 * Service interface for managing favor.
 * Defines business operations related to favor entity
 */
public interface FavorService {

    List<Favor> findAllFavour();

    Favor findFavourById(Long id);
}
