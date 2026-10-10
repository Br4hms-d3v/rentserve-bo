package be.brahms.TFE_RentServe.mappers;

import be.brahms.TFE_RentServe.models.dtos.rental.RentalByIdDTO;
import be.brahms.TFE_RentServe.models.dtos.rental.RentalDTO;
import be.brahms.TFE_RentServe.models.dtos.rental.RentalDetailEarnDTO;
import be.brahms.TFE_RentServe.models.dtos.rental.RentalEarnDTO;
import be.brahms.TFE_RentServe.models.dtos.userFavor.UserFavorNameDTO;
import be.brahms.TFE_RentServe.models.dtos.userMaterial.UserMaterialNameDTO;
import be.brahms.TFE_RentServe.models.entities.Rental;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import be.brahms.TFE_RentServe.models.forms.rental.RentalForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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

  /**
   * This map all data about the rental
   *
   * <ul>
   *   <li>Get a name of user material
   *   <li>Get a name of user favor
   *   <li>Get a total days
   *   <li>Get a total hours
   * </ul>
   *
   * @param rental the entity
   * @return a detail rental in DTO
   */
  default RentalByIdDTO toRentalDetailIdDTO(Rental rental) {
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

    // Get a local date time with date start and time start
    LocalDateTime start = LocalDateTime.of(rental.getStarDateAt(), rental.getStartTime());

    // Get a local date time with date end and time end
    LocalDateTime end = LocalDateTime.of(rental.getEndDateAt(), rental.getEndTime());

    // Get a total time between start util end
    Duration duration = Duration.between(start, end);

    long durationDays = duration.toDays();
    long durationHours = duration.toHours();

    return new RentalByIdDTO(
        rental.getId(),
        rental.getAmount(),
        rental.getStarDateAt(),
        rental.getEndDateAt(),
        rental.getStartTime(),
        rental.getEndTime(),
        nameFavor,
        nameMaterial,
        durationDays,
        durationHours);
  }

  /**
   * This method call each method toRentalDetailIdDTO (above)
   *
   * @param rentals the list of rentals
   * @return a list of each detail rental
   */
  default List<RentalByIdDTO> toRentalListDetailIdDTO(List<Rental> rentals) {
    return rentals.stream().map(this::toRentalDetailIdDTO).toList();
  }

  @Mapping(target = "dateStart", source = "starDateAt")
  @Mapping(target = "dateEnd", source = "endDateAt")
  default RentalDTO toRentalDTO (Rental rental){

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


    return new RentalDTO(
            rental.getId(),
            rental.getAmount(),
            rental.getStarDateAt(),
            rental.getEndDateAt(),
            rental.getStartTime(),
            rental.getEndTime(),
            nameFavor,
            nameMaterial

    );
  }

  // Form to Entity

  @Mapping(target = "starDateAt", source = "dateStart")
  @Mapping(target = "endDateAt", source = "dateEnd")
  Rental fromRentalForm(RentalForm rentalForm);
}
