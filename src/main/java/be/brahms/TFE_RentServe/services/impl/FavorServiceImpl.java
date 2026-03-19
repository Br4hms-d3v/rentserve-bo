package be.brahms.TFE_RentServe.services.impl;

import be.brahms.TFE_RentServe.exceptions.category.CategoryNotEmptyException;
import be.brahms.TFE_RentServe.exceptions.category.CategoryNotExistingException;
import be.brahms.TFE_RentServe.exceptions.database.DatabaseConstraintException;
import be.brahms.TFE_RentServe.exceptions.favor.FavorAlreadyExistingException;
import be.brahms.TFE_RentServe.exceptions.favor.FavorNotEmptyException;
import be.brahms.TFE_RentServe.exceptions.favor.FavorNotFoundException;
import be.brahms.TFE_RentServe.exceptions.favor.FavorException;
import be.brahms.TFE_RentServe.mappers.FavorMapper;
import be.brahms.TFE_RentServe.models.dtos.favor.FavorByIdDTO;
import be.brahms.TFE_RentServe.models.dtos.favor.FavorDTO;
import be.brahms.TFE_RentServe.models.forms.favor.FavorFormDTO;
import be.brahms.TFE_RentServe.models.forms.favor.UpdateFavorFormDTO;
import be.brahms.TFE_RentServe.models.entities.Category;
import be.brahms.TFE_RentServe.models.entities.Favor;
import be.brahms.TFE_RentServe.repositories.CategoryRepository;
import be.brahms.TFE_RentServe.repositories.FavorRepository;
import be.brahms.TFE_RentServe.services.FavorService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service implementation for managing Favor.
 * Uses FavorRepository to perform database operations.
 * Uses CategoryRepository to perform database operations
 *
 * @author Brahim K
 */
@Transactional()
@Service
public class FavorServiceImpl implements FavorService {

    private final FavorRepository favorRepository;
    private final CategoryRepository categoryRepository;
    private final FavorMapper favorMapper;

    /**
     * constructor with parameters
     *
     * @param favorRepository    the favorRepository to access favor data
     * @param categoryRepository the categoryRepository to access favor data
     * @param favorMapper        map between from to entity or dto to entity
     */
    public FavorServiceImpl(FavorRepository favorRepository, CategoryRepository categoryRepository, FavorMapper favorMapper) {
        this.favorRepository = favorRepository;
        this.categoryRepository = categoryRepository;
        this.favorMapper = favorMapper;
    }

    /**
     * Get a list of all categories
     * If the list is empty, send a message exception
     *
     * @return a list of categories
     */
    @Override
    public List<FavorDTO> findAllFavour() {
        List<Favor> favour = favorRepository.findAll();

        if (favour.isEmpty()) {
            throw new FavorException("The list is empty");
        }

        return favorMapper.toListDto(favour);
    }

    /**
     * Get favor by ID
     * If not found favor send a message exception
     *
     * @param id the identifier of favor
     * @return a details about favor
     */
    @Override
    public FavorByIdDTO findFavourById(Long id) {
        Favor favor = favorRepository.findById(id).orElseThrow(FavorNotFoundException::new);

        return favorMapper.toDtoById(favor);
    }

    /**
     * Create a new favor
     * It Checks if a name of category is already existing
     * It checks if a name of favor is already exists
     * It checks if a name of favor is not empty
     * It map the form to entity
     * If existing send a message exception
     *
     * @param form the form to create a new category
     * @return the save category
     */
    @Override
    public FavorDTO createFavor(FavorFormDTO form) {
        Favor favor = favorMapper.fromCreateFavorForm(form);
        Category categoryExist = categoryRepository.findCategoryByNameCategory(form.category());
        Boolean favorExist = favorRepository.existsFavorByNameFavor(favor.getNameFavor());

        if (form.category().isEmpty() || form.category().isBlank()) {
            throw new CategoryNotEmptyException();
        }

        if (categoryExist == null) {
            throw new CategoryNotExistingException();
        }

        if (favorExist) {
            throw new FavorAlreadyExistingException();
        }

        if (form.nameFavor().isEmpty() || form.nameFavor().isBlank()) {
            throw new FavorNotEmptyException();
        }

        favor.setNameFavor(form.nameFavor());
        favor.setAvailable(form.isAvailable());
        favor.setCategory(categoryExist);

        favorRepository.save(favor);

        return favorMapper.toDto(favor);
    }

    /**
     * Retrieves a list of favour grouped by name of category
     * Check if the name category exist
     * Check if the list of favour is not empty
     *
     * @param categoryName the name of category
     * @return a list of favour grouped by
     */
    @Override
    public List<FavorDTO> findFavourByCategoryName(String categoryName) {
        Category categoryExist = categoryRepository.findCategoryByNameCategory(categoryName);
        List<Favor> listFavour = favorRepository.findFavorsByCategoryName(categoryName);

        if (categoryExist == null) {
            throw new CategoryNotExistingException();
        }

        if (!categoryExist.getNameCategory().equals(categoryName)) {
            throw new CategoryNotExistingException();
        }

        if (categoryExist.getNameCategory().isEmpty() || categoryExist.getNameCategory().isBlank()) {
            throw new CategoryNotEmptyException();
        }

        if (listFavour.isEmpty()) {
            throw new FavorException("The list is empty");
        }

        return favorMapper.toListDto(listFavour);
    }

    /**
     * Update the favor
     * It Checks if a name of category is already existing
     * It checks if a name of favor is already exists
     * It checks if a name of favor is not empty
     *
     * @param id   the identifier of favor
     * @param form the form to edit the favor
     * @return a favor updated
     */
    @Override
    public FavorDTO updateFavor(Long id, UpdateFavorFormDTO form) {

        Favor favorId = favorRepository.findById(id).orElseThrow(FavorNotFoundException::new);
        Category categoryExist = categoryRepository.findCategoryByNameCategory(form.category());

        if (categoryExist == null) {
            throw new CategoryNotExistingException();
        }

        if (favorId.getNameFavor().equals(form.nameFavor())) {
            throw new FavorAlreadyExistingException();
        }

        if (categoryExist.getNameCategory().isEmpty() || categoryExist.getNameCategory().isBlank()) {
            throw new CategoryNotEmptyException();
        }

        if (form.nameFavor().isEmpty() || form.nameFavor().isBlank()) {
            throw new FavorNotEmptyException();
        }

        favorMapper.fromUpdateFavorForm(form, favorId);

        favorId.setNameFavor(form.nameFavor());
        favorId.setAvailable(form.isAvailable());
        favorId.setCategory(categoryExist);

        favorRepository.save(favorId);

        return favorMapper.toDto(favorId);
    }

    /**
     * Soft delete of favor
     * Check it the favor exists
     *
     * @param id the identifier of favor
     */
    @Override
    public void deleteFavor(long id) {
        Favor favor = favorRepository.findById(id).orElseThrow(FavorNotFoundException::new);
        try {
            favorRepository.delete(favor);
            favorRepository.flush();
        } catch (DataIntegrityViolationException cause) {
            throw new DatabaseConstraintException("Can't delete category because it is used by another database");
        }

    }

}
