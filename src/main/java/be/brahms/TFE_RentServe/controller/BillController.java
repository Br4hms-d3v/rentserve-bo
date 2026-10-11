package be.brahms.TFE_RentServe.controller;

import be.brahms.TFE_RentServe.hateoas.bill.BillAssembler;
import be.brahms.TFE_RentServe.models.dtos.bill.BillDTO;
import be.brahms.TFE_RentServe.services.BillService;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller manages Bill.
 *
 * <ul>
 *   <li>Display the bill by ID
 *   <li>Display the bill by month
 *   <li>Display the bill all time
 *   <li>Pay the bill
 * </ul>
 *
 * @author Brahim K
 */
@RestController
@RequestMapping("/api/bill/")
public class BillController {

  private final BillService billService;
  private final BillAssembler billAssembler;

  /**
   * This is used inject the necessary service for handling bill related request
   *
   * @param billService the service used for bill management
   * @param billAssembler the assembler used to convert Bill object to into BillDto
   */
  public BillController(BillService billService, BillAssembler billAssembler) {
    this.billService = billService;
    this.billAssembler = billAssembler;
  }

  /**
   * Get a detail about the bill
   *
   * @param id the identifier of bill
   * @return a detail bill
   */
  @GetMapping("{id}")
  @PreAuthorize("hasAnyRole('MEMBER', 'MODERATOR', 'ADMIN')")
  public ResponseEntity<EntityModel<BillDTO>> getBillById(@PathVariable long id) {
    BillDTO billId = billService.findById(id);
    return ResponseEntity.ok().body(billAssembler.toModel(billId));
  }
}
