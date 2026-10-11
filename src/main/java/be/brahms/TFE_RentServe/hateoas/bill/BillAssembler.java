package be.brahms.TFE_RentServe.hateoas.bill;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import be.brahms.TFE_RentServe.controller.BillController;
import be.brahms.TFE_RentServe.models.dtos.bill.BillDTO;
import be.brahms.TFE_RentServe.models.entities.User;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

/**
 * BillAssembler is a class that helps to convert BillDTO objects into EntityModel objects. It
 * creates models with links for bill data.
 */
@Component
public class BillAssembler implements RepresentationModelAssembler<BillDTO, EntityModel<BillDTO>> {

  /** Constructor by default for BillAssembler */
  public BillAssembler() {}

  /**
   * Convert BillDto to an EntityModel with HATEOAS links.
   *
   * @param bill the entity of bill
   * @return an EntityModel with bill data and HATEOAS
   */
  @Override
  public EntityModel<BillDTO> toModel(BillDTO bill) {
    User user = new User();
    return EntityModel.of(
        bill,
        linkTo(methodOn(BillController.class).getBillById(user.getId()))
            .withRel("Get detail about bill"));
  }
}
