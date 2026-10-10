package be.brahms.TFE_RentServe.hateoas.rental;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import be.brahms.TFE_RentServe.controller.RentalController;
import be.brahms.TFE_RentServe.models.dtos.rental.RentalByIdDTO;
import be.brahms.TFE_RentServe.models.dtos.rental.RentalDTO;
import be.brahms.TFE_RentServe.models.entities.User;
import java.util.List;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

/** RentalAssembler creates an EntityModel for rental. It also adds a link to get the rental */
@Component
public class RentalAssembler
    implements RepresentationModelAssembler<RentalByIdDTO, EntityModel<RentalByIdDTO>> {

  /** Constructor by default for RentalAssembler */
  public RentalAssembler() {}

  /**
   * Convert a RentalDto to an EntityModel with HATEOAS links
   *
   * <p>This method adds useful links to the RentalDto, like a link to the rental by ID
   *
   * <p>The generated model contains links to:
   *
   * <ul>
   *   <li>Rental detail by id
   * </ul>
   *
   * @param rental the rental
   * @return an EntityModel with the rental data and HATEOAS links
   */
  @Override
  public EntityModel<RentalByIdDTO> toModel(RentalByIdDTO rental) {
    return EntityModel.of(
        rental,
        linkTo(methodOn(RentalController.class).detailRentalById(rental.id()))
            .withRel("Detail rental"),
        linkTo(methodOn(RentalController.class).findRentalNotPaidUser(rental.id()))
            .withRel("Rental not-paid-user"));
  }

  /**
   * Convert a RentalDto to an EntityModel with HATEOAS links
   *
   * <p>This method adds useful links to the RentalDto, like a link to the rental by ID
   *
   * <p>The generated model contains links to:
   *
   * <ul>
   *   <li>Create a rental
   * </ul>
   *
   * @param rental the rental
   * @return an EntityModel with the rental data and HATEOAS links
   */
  public EntityModel<RentalDTO> toModel(RentalDTO rental) {
    return EntityModel.of(
        rental,
        linkTo(methodOn(RentalController.class).newRental(rental.id(), null))
            .withRel("Detail rental"));
  }

  /**
   * Convert a RentalByIdDTO to an CollectionModel with HATEOAS links
   *
   * <p>This method adds useful links to the RentalByIdDTO.
   *
   * <p>The generated model contains links to:
   *
   * <ul>
   *   <li>List rental not paid yet
   * </ul>
   *
   * @param rental the rental
   * @return an EntityModel with the rental data and HATEOAS links
   */
  public CollectionModel<RentalByIdDTO> toCollectionModel(List<RentalByIdDTO> rental) {
    User userId = new User();
    return CollectionModel.of(
        rental,
        linkTo(methodOn(RentalController.class).findRentalNotPaidUser(userId.getId()))
            .withRel("Rental not-paid"));
  }
}
