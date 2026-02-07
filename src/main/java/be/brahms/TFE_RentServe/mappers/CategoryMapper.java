package be.brahms.TFE_RentServe.mappers;

import be.brahms.TFE_RentServe.models.dtos.category.CategoryDTO;
import be.brahms.TFE_RentServe.models.dtos.category.CategoryIdDTO;
import be.brahms.TFE_RentServe.models.entities.Category;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    // Entity to DTO
    CategoryDTO toDto(Category category);

    CategoryIdDTO toIdDto(Category category);
}
