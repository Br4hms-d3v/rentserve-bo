package be.brahms.TFE_RentServe.hateoas.rental;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import be.brahms.TFE_RentServe.controller.EarnController;
import be.brahms.TFE_RentServe.models.dtos.rental.RentalEarnDTO;
import be.brahms.TFE_RentServe.models.entities.User;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

/**
 * EarnAssembler creates an EntityModel for rental earnings. It also adds a link to get the user's
 * total earnings.
 */
@Component
public class EarnAssembler
    implements RepresentationModelAssembler<RentalEarnDTO, EntityModel<RentalEarnDTO>> {

  /** Constructor by default for EarnAssembler */
  public EarnAssembler() {}

  @Override
  public EntityModel<RentalEarnDTO> toModel(RentalEarnDTO rental) {
    User user = new User();
    return EntityModel.of(
        rental,
        linkTo(methodOn(EarnController.class).totalEarned(user.getId(), null, null))
            .withRel("Get my total earn"));
  }
}
