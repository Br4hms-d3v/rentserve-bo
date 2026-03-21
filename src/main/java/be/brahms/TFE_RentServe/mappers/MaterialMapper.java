package be.brahms.TFE_RentServe.mappers;

import be.brahms.TFE_RentServe.models.dtos.material.MaterialDTO;
import be.brahms.TFE_RentServe.models.entities.Material;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper responsible for converting material entity
 * to various material related DTOs and update from form objects
 * <p>
 * This mapper is used to handle material data transformations
 * between the domain layer and Api layer
 * </p>
 *
 * @author Brahim k
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MaterialMapper {

    // Entity to DTO

    /**
     * Convert Material to a {@code List<MaterialDto}
     *
     * @param materials the list of material entity
     * @return the list of Material Dto
     */
    List<MaterialDTO> toListDto(List<Material> materials);


    // Form to Entity
}
