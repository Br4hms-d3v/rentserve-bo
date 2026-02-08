package be.brahms.TFE_RentServe.mappers;

import be.brahms.TFE_RentServe.models.dtos.category.CategoryDTO;
import be.brahms.TFE_RentServe.models.dtos.category.CategoryIdDTO;
import be.brahms.TFE_RentServe.models.entities.Category;
import be.brahms.TFE_RentServe.models.forms.category.CategoryForm;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    // Entity to DTO
    CategoryDTO toDto(Category category);

    CategoryIdDTO toIdDto(Category category);

    List<CategoryDTO> toListDto(List<Category> categories);

    // Form to Entity
    Category fromCategoryForm(CategoryForm form);

    Category fromUpdateCategoryForm(CategoryForm form, @MappingTarget Category category);
}
