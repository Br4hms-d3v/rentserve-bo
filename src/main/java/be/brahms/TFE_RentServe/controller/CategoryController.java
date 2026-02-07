package be.brahms.TFE_RentServe.controller;

import be.brahms.TFE_RentServe.hateoas.category.CategoryAssembler;
import be.brahms.TFE_RentServe.mappers.CategoryMapper;
import be.brahms.TFE_RentServe.models.dtos.category.CategoryDTO;
import be.brahms.TFE_RentServe.models.dtos.category.CategoryIdDTO;
import be.brahms.TFE_RentServe.models.entities.Category;
import be.brahms.TFE_RentServe.services.CategoryService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
