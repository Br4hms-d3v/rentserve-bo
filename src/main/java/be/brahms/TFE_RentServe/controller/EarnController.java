package be.brahms.TFE_RentServe.controller;

import be.brahms.TFE_RentServe.hateoas.rental.EarnAssembler;
import be.brahms.TFE_RentServe.models.dtos.rental.RentalEarnDTO;
import be.brahms.TFE_RentServe.services.RentalService;
import java.time.LocalDate;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * This controller manages Rental. It has a method to display how much the owner earn. It has a
 * methode to display detail what it's earn
 *
 * @author Brahim K
 */
@RestController
@RequestMapping("/api/earn/")
public class EarnController {

  private final RentalService rentalService;
  private final EarnAssembler earnAssembler;

  /**
   * This constructor is used to inject the necessary service for handling rental related request
   *
   * @param rentalService the service used for rental management
   * @param earnAssembler the assembler to convert Earn to into RentalEarnDto
   */
  public EarnController(RentalService rentalService, EarnAssembler earnAssembler) {
    this.rentalService = rentalService;
    this.earnAssembler = earnAssembler;
  }

  /**
   * Get my earn between date start and date end
   *
   * @param id the identifier
   * @param dateStart the date start
   * @param dateEnd the date end
   * @return the total amount
   */
  @GetMapping("/my-earned/{id}")
  @PreAuthorize("hasAnyRole('MEMBER', 'MODERATOR', 'ADMIN')")
  public ResponseEntity<EntityModel<RentalEarnDTO>> totalEarned(
      @PathVariable long id, @RequestParam LocalDate dateStart, @RequestParam LocalDate dateEnd) {
    RentalEarnDTO myEarned = rentalService.totalEarned(id, dateStart, dateEnd);
    return ResponseEntity.ok().body(earnAssembler.toModel(myEarned));
  }

  /**
   * Get earned this month
   *
   * @param id the identifier of user
   * @return the total amount
   */
  @GetMapping("/my-earned-this-month/{id}")
  @PreAuthorize("hasAnyRole('MEMBER', 'MODERATOR', 'ADMIN')")
  public ResponseEntity<EntityModel<RentalEarnDTO>> totalEarnedThisMonth(@PathVariable long id) {
    RentalEarnDTO myEarned = rentalService.totalEarnedThisMonth(id);
    return ResponseEntity.ok().body(earnAssembler.toModel(myEarned));
  }
}
