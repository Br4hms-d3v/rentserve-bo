package be.brahms.TFE_RentServe.controller;

import be.brahms.TFE_RentServe.hateoas.rental.RentalAssembler;
import be.brahms.TFE_RentServe.models.dtos.rental.RentalByIdDTO;
import be.brahms.TFE_RentServe.models.dtos.rental.RentalDTO;
import be.brahms.TFE_RentServe.models.forms.rental.RentalForm;
import be.brahms.TFE_RentServe.services.RentalService;
import java.util.List;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * This controller manages Rental. It has a method to display detail about the rental It has a
 * method to display rentals not paid yet
 *
 * @author Brahim K
 */
@RestController
@RequestMapping("/api/rental/")
public class RentalController {
  private final RentalService rentalService;
  private final RentalAssembler rentalAssembler;

  /**
   * This constructor is used to inject necessary service for handling rental related request
   *
   * @param rentalService the service used for rental management
   * @param rentalAssembler the assembler to convert Rental to into RentalDto
   */
  public RentalController(RentalService rentalService, RentalAssembler rentalAssembler) {
    this.rentalService = rentalService;
    this.rentalAssembler = rentalAssembler;
  }

  /**
   * Get a detail about the rental
   *
   * @param id the identifier
   * @return a detail about the rental
   */
  @GetMapping("{id}")
  @PreAuthorize("hasAnyRole('MEMBER', 'MODERATOR', 'ADMIN')")
  public ResponseEntity<EntityModel<RentalByIdDTO>> detailRentalById(@PathVariable long id) {
    RentalByIdDTO myDetailRental = rentalService.findRentalById(id);
    return ResponseEntity.ok(this.rentalAssembler.toModel(myDetailRental));
  }

  /**
   * Get a list of rental not paid yet
   *
   * @param id the identifier of user
   * @return a list of rental not paid yet
   */
  @GetMapping("user/{id}")
  @PreAuthorize("hasAnyRole('MEMBER', 'MODERATOR', 'ADMIN')")
  public ResponseEntity<CollectionModel<RentalByIdDTO>> findRentalNotPaidUser(
      @PathVariable long id) {
    List<RentalByIdDTO> rentalNotPaid = rentalService.findRentalUser(id);
    CollectionModel<RentalByIdDTO> rentalNotPaidModel =
        rentalAssembler.toCollectionModel(rentalNotPaid);
    return ResponseEntity.ok().body(rentalNotPaidModel);
  }

  @PostMapping("new-rental/{userId}")
  @PreAuthorize("hasAnyRole('MEMBER', 'MODERATOR', 'ADMIN')")
  public ResponseEntity<EntityModel<RentalDTO>> newRental(@PathVariable long userId, @RequestBody RentalForm form) {
    RentalDTO newRental = rentalService.createRental(userId, form);

    return ResponseEntity.ok().body(rentalAssembler.toModel(newRental));
  }
}
