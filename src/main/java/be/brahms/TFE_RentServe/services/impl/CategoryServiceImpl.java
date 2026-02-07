package be.brahms.TFE_RentServe.services.impl;

import be.brahms.TFE_RentServe.exceptions.category.CategoryException;
import be.brahms.TFE_RentServe.exceptions.category.CategoryExistingException;
import be.brahms.TFE_RentServe.exceptions.category.CategoryNotFoundException;
import be.brahms.TFE_RentServe.mappers.CategoryMapper;
import be.brahms.TFE_RentServe.models.entities.Category;
import be.brahms.TFE_RentServe.models.forms.category.CategoryForm;
import be.brahms.TFE_RentServe.repositories.CategoryRepository;
import be.brahms.TFE_RentServe.services.CategoryService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
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

    @Override
    public Category createCategory(CategoryForm form) {
        Category category = categoryMapper.fromCategoryForm(form);

        if (categoryRepository.existsCategoryByNameCategory(category.getNameCategory())) {
            throw new CategoryExistingException();
        }

        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(long id, CategoryForm form) {
        Category categoryId = categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);

        boolean categoryNameExists = categoryRepository.existsCategoryByNameCategory(categoryId.getNameCategory());

        if (categoryNameExists && categoryId.getNameCategory().equals(form.nameCategory())) {
            throw new CategoryExistingException();
        }

        categoryId.setNameCategory(form.nameCategory());
        categoryId.setUpdatedAt(LocalDate.now());
        categoryMapper.fromUpdateCategoryForm(form, categoryId);

        return categoryRepository.save(categoryId);
    }
}
