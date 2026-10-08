package be.brahms.TFE_RentServe.mappers;

import be.brahms.TFE_RentServe.models.dtos.rental.RentalEarnDTO;
import java.math.BigDecimal;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper responsible for converting rental entity to various rental related DTOs and update from
 * form objects
 *
 * <p>This mapper is used to handle rental data transformations between the domain layer and Api
 * layer
 *
 * @author Brahim k
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RentalMapper {

  // Entity to Data Access Object

  /**
   * Maps the rental to toEarnDTO
   *
   * @param totalEarned the total amount earned
   * @return a Rental DTO
   */
  RentalEarnDTO toEarnDTO(BigDecimal totalEarned);
}
