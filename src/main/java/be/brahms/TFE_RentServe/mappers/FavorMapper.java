package be.brahms.TFE_RentServe.mappers;

import be.brahms.TFE_RentServe.models.dtos.favor.FavorByIdDTO;
import be.brahms.TFE_RentServe.models.dtos.favor.FavorDTO;
import be.brahms.TFE_RentServe.models.dtos.favor.UpdateFavorFormDTO;
import be.brahms.TFE_RentServe.models.entities.Favor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper responsible for converting favor entity
 * to various favor related DTOs and updating from form objects
 * <p>
 * This mapper is used to handle favor data transformations
 * between the domain layer and Api layer
 * </p>
 *
 * @author Brahim k
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FavorMapper {

    // Entity to DTO

    /**
     * Convert Favor to a FavorDto
     *
     * @param favor the favor Entity
     * @return the Favor Dto
     */
    FavorDTO toDto(Favor favor);

    /**
     * Converts a Favor entity to FavorByIdDto
     * It takes the category name from Favor
     * and puts it into the field nameCategory in FavorByIdDto
     *
     * @param favor the Favor object to convert
     * @return a FavorByIdDTO object with data from Favor
     */
    @Mapping(source = "category.nameCategory", target = "nameCategory")
    FavorByIdDTO toDtoById(Favor favor);

    /**
     * Convert Favor to a List<FavorDto>
     *
     * @param favors the list of favor Entity
     * @return the list of Favor Dto
     */
    List<FavorDTO> toListDto(List<Favor> favors);

    // Form to Entity

    /**
     * Update an existing Favor entity with data from a UpdateFavorForm.
     * Used when update a favor
     *
     * @param form  the favor form
     * @param favor the existing favor form
     */
    @Mapping(target = "category", ignore = true)
    void fromUpdateFavorForm(UpdateFavorFormDTO form, @MappingTarget Favor favor);
}
