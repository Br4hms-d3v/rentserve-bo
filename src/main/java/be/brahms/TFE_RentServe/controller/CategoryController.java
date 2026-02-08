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
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This controller manages Category
 */
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryAssembler categoryAssembler;
    private final CategoryMapper categoryMapper;

    public CategoryController(CategoryService categoryService, CategoryAssembler categoryAssembler, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryAssembler = categoryAssembler;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping("list")
    public ResponseEntity<CollectionModel<CategoryDTO>> getAllCategories() {
        List<Category> categories = categoryService.findAllCategories();
        List<CategoryDTO> listCategoriesDto = categories
                .stream()
                .map(categoryMapper::toDto)
                .toList();

        return ResponseEntity.ok().body(CollectionModel.of(listCategoriesDto));
    }

    @GetMapping("{id}")
    public ResponseEntity<EntityModel<CategoryIdDTO>> getCategory(@PathVariable long id) {
        Category categoryId = categoryService.findCategoryById(id);
        CategoryIdDTO categoryIdDTO = categoryMapper.toIdDto(categoryId);
        return ResponseEntity.ok(categoryAssembler.toIdModel(categoryIdDTO));
    }

    @PostMapping("{new}")
    public ResponseEntity<EntityModel<CategoryDTO>> createCategory(@RequestBody @Valid CategoryForm form) {
        Category category = categoryService.createCategory(form);
        CategoryDTO categoryDTO = categoryMapper.toDto(category);
        return ResponseEntity.ok().body(categoryAssembler.toModel(categoryDTO));
    }

    @PutMapping("edit/{id}")
    public ResponseEntity<EntityModel<CategoryDTO>> updateCategory(@PathVariable long id, @RequestBody CategoryForm form) {
        Category category = categoryService.updateCategory(id, form);
        CategoryDTO categoryDTO = categoryMapper.toDto(category);
        return ResponseEntity.ok().body(categoryAssembler.toModel(categoryDTO));
    }

    @GetMapping("search/{nameCategory}")
    public ResponseEntity<CollectionModel<CategoryDTO>> searchCategory(@PathVariable String nameCategory) {
        List<Category> categories = categoryService.searchCategory(nameCategory);
        List<CategoryDTO> categoriesDTO = categoryMapper.toListDto(categories);
        return ResponseEntity.ok().body(CollectionModel.of(categoriesDTO));
    }

}
