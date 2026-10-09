package be.brahms.TFE_RentServe.controller;

import be.brahms.TFE_RentServe.hateoas.rental.RentalAssembler;
import be.brahms.TFE_RentServe.models.dtos.rental.RentalByIdDTO;
import be.brahms.TFE_RentServe.services.RentalService;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
