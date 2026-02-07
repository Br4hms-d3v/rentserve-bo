package be.brahms.TFE_RentServe.services;

import be.brahms.TFE_RentServe.models.entities.Category;

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
}
