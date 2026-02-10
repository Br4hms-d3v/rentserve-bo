package be.brahms.TFE_RentServe.mappers;

import be.brahms.TFE_RentServe.models.dtos.category.CategoryDTO;
import be.brahms.TFE_RentServe.models.dtos.category.CategoryIdDTO;
import be.brahms.TFE_RentServe.models.entities.Category;
import be.brahms.TFE_RentServe.models.forms.category.CategoryForm;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper responsible for converting category entity
 * to various category-related DTOs and updating from form objects
 * <p>
 * This mapper is used to handle category data transformations
 * between the domain layer and Api layer
 * </p>
 *
 * @author Brahim K
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    // Entity to DTO

    /**
     * Convert a Category to a CategoryDto
     *
     * @param category the Category entity
     * @return the Category Dto
     */
    CategoryDTO toDto(Category category);

    /**
     * Convert a Category entity to a CategoryIdDTO.
     * This DTO contains only the id of the category.
     *
     * @param category the Category entity
     * @return the CategoryIdDTO
     */
    CategoryIdDTO toIdDto(Category category);

    /**
     * Convert a list of Category entities to a list of CategoryDTO.
     *
     * @param categories the list of Category entities
     * @return the list of CategoryDTO
     */
    List<CategoryDTO> toListDto(List<Category> categories);

    // Form to Entity

    /**
     * Convert a CategoryForm to a Category entity.
     * Used when creating a new category.
     *
     * @param form the category form
     * @return the Category entity
     */
    Category fromCategoryForm(CategoryForm form);

    /**
     * Update an existing Category entity with data from a CategoryForm.
     * Used when updating a category.
     *
     * @param form     the category form
     * @param category the existing Category entity
     * @return the updated Category entity
     */
    Category fromUpdateCategoryForm(CategoryForm form, @MappingTarget Category category);
}
