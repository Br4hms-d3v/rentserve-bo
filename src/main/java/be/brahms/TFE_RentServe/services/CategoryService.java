package be.brahms.TFE_RentServe.services;

import be.brahms.TFE_RentServe.models.entities.Category;
import be.brahms.TFE_RentServe.models.forms.category.CategoryForm;
import jakarta.validation.Valid;

import java.util.List;

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

    Category updateCategory(long id, @Valid CategoryForm form);
}
