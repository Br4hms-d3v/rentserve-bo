package be.brahms.TFE_RentServe.services.impl;

import be.brahms.TFE_RentServe.exceptions.category.CategoryException;
import be.brahms.TFE_RentServe.exceptions.category.CategoryNotFoundException;
import be.brahms.TFE_RentServe.models.entities.Category;
import be.brahms.TFE_RentServe.repositories.CategoryRepository;
import be.brahms.TFE_RentServe.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAllCategories() {

        List<Category> categories = categoryRepository.findAll();

        if (categories.isEmpty()) {
            throw new CategoryException("La liste est vide");
        }
        return categories;
    }

    @Override
    public Category findCategoryById(long id) {
        Category category = categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);

        return category;
    }
}
