package be.brahms.TFE_RentServe.controller;

import be.brahms.TFE_RentServe.hateoas.category.CategoryAssembler;
import be.brahms.TFE_RentServe.mappers.CategoryMapper;
import be.brahms.TFE_RentServe.models.dtos.category.CategoryDTO;
import be.brahms.TFE_RentServe.models.dtos.category.CategoryIdDTO;
import be.brahms.TFE_RentServe.models.entities.Category;
import be.brahms.TFE_RentServe.models.forms.category.CategoryForm;
import be.brahms.TFE_RentServe.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This controller manages Category
 * It has a method to display a list of categories
 * It has a method to display details about the category
 * It has a method to create a new category
 * It has a method to edit a category
 * It has a method to search by name of category
 * It has a method to delete a category
 *
 * @author Brahim K
 */
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryAssembler categoryAssembler;
    private final CategoryMapper categoryMapper;

    /**
     * This constructor is used to inject the necessary services for handling category-related request.
     *
     * @param categoryService   the service used for category management
     * @param categoryAssembler the assembler used to convert Category object to into CategoryDto models
     * @param categoryMapper    hateoas create the link to redirect to endPoints
     */
    public CategoryController(CategoryService categoryService, CategoryAssembler categoryAssembler, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryAssembler = categoryAssembler;
        this.categoryMapper = categoryMapper;
    }

    /**
     * Get a list of all categories
     *
     * @return a list of all categories
     */
    @GetMapping("list")
    @PreAuthorize("hasAnyRole('MEMBER', 'MODERATOR', 'ADMIN')")
    public ResponseEntity<CollectionModel<CategoryDTO>> getAllCategories() {
        List<Category> categories = categoryService.findAllCategories();
        List<CategoryDTO> listCategoriesDto = categories
                .stream()
                .map(categoryMapper::toDto)
                .toList();

        return ResponseEntity.ok().body(CollectionModel.of(listCategoriesDto));
    }

    /**
     * Get category's data by his ID
     *
     * @param id identifier unique
     * @return data's about the category
     */
    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('MEMBER', 'MODERATOR', 'ADMIN')")
    public ResponseEntity<EntityModel<CategoryIdDTO>> getCategory(@PathVariable long id) {
        Category categoryId = categoryService.findCategoryById(id);
        CategoryIdDTO categoryIdDTO = categoryMapper.toIdDto(categoryId);
        return ResponseEntity.ok(categoryAssembler.toIdModel(categoryIdDTO));
    }

    /**
     * Create a new category
     *
     * @param form the form to create a new category
     * @return a new category
     */
    @PostMapping("{new}")
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    public ResponseEntity<EntityModel<CategoryDTO>> createCategory(@RequestBody @Valid CategoryForm form) {
        Category category = categoryService.createCategory(form);
        CategoryDTO categoryDTO = categoryMapper.toDto(category);
        return ResponseEntity.ok().body(categoryAssembler.toModel(categoryDTO));
    }

    /**
     * This method update some data's about the category
     * Check if the name of category already exist
     *
     * @param id   the identifier of category
     * @param form the form with new data to update
     * @return a new response with data updated
     */
    @PutMapping("edit/{id}")
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    public ResponseEntity<EntityModel<CategoryDTO>> updateCategory(@PathVariable long id, @RequestBody CategoryForm form) {
        Category category = categoryService.updateCategory(id, form);
        CategoryDTO categoryDTO = categoryMapper.toDto(category);
        return ResponseEntity.ok().body(categoryAssembler.toModel(categoryDTO));
    }

    /**
     * This method to search a name of category
     *
     * @param nameCategory the name of category
     * @return a list of category
     */
    @GetMapping("search/{nameCategory}")
    @PreAuthorize("hasAnyRole('MEMBER', 'MODERATOR', 'ADMIN')")
    public ResponseEntity<CollectionModel<CategoryDTO>> searchCategory(@PathVariable String nameCategory) {
        List<Category> categories = categoryService.searchCategory(nameCategory);
        List<CategoryDTO> categoriesDTO = categoryMapper.toListDto(categories);
        return ResponseEntity.ok().body(CollectionModel.of(categoriesDTO));
    }

    /**
     * This method to delete
     *
     * @param id the identifier
     * @return a message to confirm deleting
     */
    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteCategory(@PathVariable long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().body("La catégorie a été supprimée avec succès.");
    }

}
