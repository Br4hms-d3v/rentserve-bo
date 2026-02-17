package be.brahms.TFE_RentServe.mappers;

import be.brahms.TFE_RentServe.models.dtos.favor.FavorByIdDTO;
import be.brahms.TFE_RentServe.models.dtos.favor.FavorDTO;
import be.brahms.TFE_RentServe.models.entities.Favor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FavorMapper {

    FavorDTO toDto(Favor favor);

    @Mapping(source = "category.nameCategory", target = "nameCategory")
    FavorByIdDTO toDtoById(Favor favor);
}
