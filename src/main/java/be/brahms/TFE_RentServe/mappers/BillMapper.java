package be.brahms.TFE_RentServe.mappers;

import be.brahms.TFE_RentServe.models.dtos.bill.BillDTO;
import be.brahms.TFE_RentServe.models.entities.Bill;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper responsible for converting bill entity to various bill related DTOs and update from form
 * objects
 *
 * <p>This mapper is used to handle bill data transformations between the domain layer and Api layer
 *
 * @author Brahim k
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BillMapper {

  // Entity to Data Access Object

  /**
   * Maps the bill to billDTO
   *
   * @param bill the bill
   * @return the bill DTO
   */
  BillDTO toBillDetailDTO(Bill bill);
}
