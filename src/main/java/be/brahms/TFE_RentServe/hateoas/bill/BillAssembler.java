package be.brahms.TFE_RentServe.hateoas.bill;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import be.brahms.TFE_RentServe.controller.BillController;
import be.brahms.TFE_RentServe.models.dtos.bill.BillDTO;
import be.brahms.TFE_RentServe.models.entities.User;
import java.util.List;
import org.springframework.hateoas.CollectionModel;
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

  /**
   * Convert a billDto to EntityModel with HATEOAS links. This method adds useful links to the bill
   *
   * @param bills the list of bill
   * @return a list of collection whit HATEOAS links
   */
  public CollectionModel<BillDTO> toCollectionModel(List<BillDTO> bills) {
    User user = new User();
    return CollectionModel.of(
        bills,
        linkTo(methodOn(BillController.class).getBillList(user.getId()))
            .withRel("Get a list of bills"),
        linkTo(methodOn(BillController.class).getCurrentMonth(user.getId()))
            .withRel("Get a list of bills current month"));
  }
}
