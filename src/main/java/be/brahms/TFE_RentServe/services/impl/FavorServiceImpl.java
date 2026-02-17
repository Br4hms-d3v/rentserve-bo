package be.brahms.TFE_RentServe.services.impl;

import be.brahms.TFE_RentServe.exceptions.favor.FavorNotFoundException;
import be.brahms.TFE_RentServe.exceptions.favor.FavourException;
import be.brahms.TFE_RentServe.models.entities.Favor;
import be.brahms.TFE_RentServe.repositories.FavorRepository;
import be.brahms.TFE_RentServe.services.FavorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service implementation for managing Favor.
 * Uses FavorRepository to perform database operations.
 */
@Transactional(readOnly = true)
@Service
public class FavorServiceImpl implements FavorService {

    private FavorRepository favorRepository;

    public FavorServiceImpl(FavorRepository favorRepository) {
        this.favorRepository = favorRepository;
    }

    @Override
    public List<Favor> findAllFavour() {
        List<Favor> favour = favorRepository.findAll();

        if (favour.isEmpty()) {
            throw new FavourException("La liste est vide");
        }

        return favour;
    }

    @Override
    public Favor findFavourById(Long id) {
        return favorRepository.findById(id).orElseThrow(FavorNotFoundException::new);
    }

}
