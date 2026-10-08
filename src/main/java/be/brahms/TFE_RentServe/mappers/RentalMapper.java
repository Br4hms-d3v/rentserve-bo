package be.brahms.TFE_RentServe.mappers;

import be.brahms.TFE_RentServe.models.dtos.rental.RentalDetailEarnDTO;
import be.brahms.TFE_RentServe.models.dtos.rental.RentalEarnDTO;
import be.brahms.TFE_RentServe.models.dtos.userFavor.UserFavorNameDTO;
import be.brahms.TFE_RentServe.models.dtos.userMaterial.UserMaterialNameDTO;
import be.brahms.TFE_RentServe.models.entities.Rental;
import java.math.BigDecimal;
import java.util.List;
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

  /**
   * Get detail about each amount earned
   *
   * @param rental the rental entity
   * @return the detail earned each amount
   */
  default RentalDetailEarnDTO toDetailEarnDTO(Rental rental) {

    // Get the name of favor if it's empty let null
    UserFavorNameDTO nameFavor =
        rental.getUserFavor() != null
            ? new UserFavorNameDTO(rental.getUserFavor().getFavor().getNameFavor())
            : null;

    // Get the name of material if it's empty let null
    UserMaterialNameDTO nameMaterial =
        rental.getUserMaterial() != null
            ? new UserMaterialNameDTO(rental.getUserMaterial().getMaterial().getNameMaterial())
            : null;

    return new RentalDetailEarnDTO(
        rental.getCreatedAt(),
        nameFavor,
        nameMaterial,
        rental.getAmount(),
        rental.getBill().getStatus().name());
  }

  /**
   * This method call each method detailEarnDto (above)
   *
   * @param rentals the list of rentals
   * @return a list of each detail amount
   */
  default List<RentalDetailEarnDTO> toDetailEarnDTO(List<Rental> rentals) {
    return rentals.stream().map(this::toDetailEarnDTO).toList();
  }
}
