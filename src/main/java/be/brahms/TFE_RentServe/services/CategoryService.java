package be.brahms.TFE_RentServe.services;

import be.brahms.TFE_RentServe.models.entities.Category;
import be.brahms.TFE_RentServe.models.forms.category.CategoryForm;
import jakarta.validation.Valid;

import java.util.List;

/**
 * Service interface for managing category.
 * Defines business operations related to category entities.
 */
public interface CategoryService {

    /**
     * This method get a list of categories
     *
     * @return list of categories
     */
    List<Category> findAllCategories();

    /**
     * This method get a category by ID
     *
     * @param id the identifier of category
     * @return details about the category
     */
    Category findCategoryById(long id);

    /**
     * This method saves a new category.
     *
     * @param form the form to create a new category
     * @return the saved category
     */
    Category createCategory(@Valid CategoryForm form);

    /**
     * This method edit the category
     *
     * @param id   the identifier of category
     * @param form the form to edit the category
     * @return the updated category
     */
    Category updateCategory(long id, @Valid CategoryForm form);

    /**
     * This method search a category by his name
     *
     * @param nameCategory the name of category
     * @return a list of category
     */
    List<Category> searchCategory(String nameCategory);

    /**
     * Soft delete category
     *
     * @param id the identifier of category
     */
    void deleteCategory(long id);
}
