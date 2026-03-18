package be.brahms.TFE_RentServe.services.impl;

import be.brahms.TFE_RentServe.exceptions.category.CategoryException;
import be.brahms.TFE_RentServe.exceptions.category.CategoryExistingException;
import be.brahms.TFE_RentServe.exceptions.category.CategoryNotFoundException;
import be.brahms.TFE_RentServe.exceptions.database.DatabaseConstraintException;
import be.brahms.TFE_RentServe.mappers.CategoryMapper;
import be.brahms.TFE_RentServe.models.dtos.category.CategoryDTO;
import be.brahms.TFE_RentServe.models.dtos.category.CategoryIdDTO;
import be.brahms.TFE_RentServe.models.entities.Category;
import be.brahms.TFE_RentServe.models.forms.category.CategoryForm;
import be.brahms.TFE_RentServe.repositories.CategoryRepository;
import be.brahms.TFE_RentServe.services.CategoryService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Service implementation for managing Category.
 * Uses CategoryRepository to perform database operations
 * Uses CategoryMapper to map between form to entity or dto to entity
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    /**
     * Constructor with parameters
     *
     * @param categoryRepository the categoryRepository to access category data
     * @param categoryMapper     map between form category to entity or dto to entity
     */
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    /**
     * Get a list of all categories
     * If the list is empty, send a message exception
     *
     * @return a list of categories
     */
    @Override
    public List<CategoryDTO> findAllCategories() {

        List<Category> categories = categoryRepository.findAll();

        if (categories.isEmpty()) {
            throw new CategoryException("La liste est vide");
        }
        return categoryMapper.toListDto(categories);
    }

    /**
     * Get category by ID
     * If not found category send a message exception
     *
     * @param id the identifier of category
     * @return a details about category
     */
    @Override
    public CategoryIdDTO findCategoryById(long id) {
        Category category = categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);

        return categoryMapper.toIdDto(category);
    }

    /**
     * Create a new Category
     * It Checks if a name of category is already existing
     * If existing send a message exception
     *
     * @param form the form to create a new category
     * @return the save category
     */
    @Override
    public CategoryDTO createCategory(CategoryForm form) {

        Category category = categoryMapper.fromCategoryForm(form);
        Category categoryNameExists = categoryRepository.findCategoryByNameCategory(category.getNameCategory());

        if (categoryNameExists != null) {
            throw new CategoryExistingException();
        }

        categoryRepository.save(category);

        return categoryMapper.toDto(category);
    }

    /**
     * Update the category
     *
     * @param id   the identifier of category
     * @param form the form to edit the category
     * @return a category updated
     */
    @Override
    public CategoryDTO updateCategory(long id, CategoryForm form) {
        Category categoryId = categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);

        Category categoryNameExists = categoryRepository.findCategoryByNameCategory(categoryId.getNameCategory());

        if (categoryNameExists.getNameCategory().equals(form.nameCategory())) {
            throw new CategoryExistingException();
        }

        categoryId.setNameCategory(form.nameCategory());
        categoryId.setUpdatedAt(LocalDate.now());

        categoryMapper.fromUpdateCategoryForm(form, categoryId);

        return categoryMapper.toDto(categoryRepository.save(categoryId));
    }

    /**
     * Retrieves a list of categories
     * If the list is empty send a message exception
     *
     * @param nameCategory the name of category
     * @return a list of categories
     */
    @Override
    public List<CategoryDTO> searchCategory(String nameCategory) {
        List<Category> categories = categoryRepository.searchCategory(nameCategory);

        if (categories.isEmpty()) {
            throw new CategoryNotFoundException("The list is empty");
        }

        return categories
                .stream()
                .map(categoryMapper::toDto)
                .toList();

    }

    /**
     * Soft dele of category
     * If data is used by another tables send a message exception
     * If not used by another, delete it
     * @param id the identifier of category
     */
    @Override
    public void deleteCategory(long id) {
        boolean categoryIdExist = categoryRepository.existsCategoryById(id);
        Category category = categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);

        if(categoryIdExist) {
            throw new DatabaseConstraintException("Can't delete category because it is used by another database");
        }
        try {
            categoryRepository.delete(category);
        } catch (DataIntegrityViolationException exception) {
            throw new DatabaseConstraintException("Can't delete: " + exception.getMessage());
        }

    }
}
