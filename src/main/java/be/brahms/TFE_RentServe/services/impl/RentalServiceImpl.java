package be.brahms.TFE_RentServe.services.impl;

import be.brahms.TFE_RentServe.enums.Status;
import be.brahms.TFE_RentServe.exceptions.rental.RentalException;
import be.brahms.TFE_RentServe.exceptions.rental.RentalNotFoundException;
import be.brahms.TFE_RentServe.exceptions.user.UserException;
import be.brahms.TFE_RentServe.exceptions.user.UserNotFoundException;
import be.brahms.TFE_RentServe.exceptions.userFavor.UserFavorNotFoundException;
import be.brahms.TFE_RentServe.exceptions.userMaterial.UserMaterialNotFoundException;
import be.brahms.TFE_RentServe.mappers.RentalMapper;
import be.brahms.TFE_RentServe.models.dtos.rental.RentalByIdDTO;
import be.brahms.TFE_RentServe.models.dtos.rental.RentalDTO;
import be.brahms.TFE_RentServe.models.dtos.rental.RentalDetailEarnDTO;
import be.brahms.TFE_RentServe.models.dtos.rental.RentalEarnDTO;
import be.brahms.TFE_RentServe.models.entities.*;
import be.brahms.TFE_RentServe.models.forms.rental.RentalForm;
import be.brahms.TFE_RentServe.models.forms.rental.RentalUpdateForm;
import be.brahms.TFE_RentServe.repositories.*;
import be.brahms.TFE_RentServe.services.RentalService;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service implementation for managing Rental. Uses RentalRepository to perform database operations
 * uses RentalMapper to map between form to entity or dto to entity
 *
 * @author Brahim K
 */
@Service
public class RentalServiceImpl implements RentalService {

  private final RentalRepository rentalRepository;
  private final RentalMapper rentalMapper;
  private final UserRepository userRepository;
  private final BillRepository billRepository;
  private final UserMaterialRepository userMaterialRepository;
  private final UserFavorRepository userFavorRepository;

  /**
   * Constructor with params
   *
   * @param rentalRepository the rentalRepo to access rental data
   * @param rentalMapper map between from Rental to entity or dto to entity
   * @param userRepository the userRepo to access user data
   * @param billRepository the billRepo to access bill data
   * @param userMaterialRepository the userMaterialRepo to access user material data
   * @param userFavorRepository the userFavorRep to access user favor data
   */
  public RentalServiceImpl(
      RentalRepository rentalRepository,
      RentalMapper rentalMapper,
      UserRepository userRepository,
      BillRepository billRepository,
      UserMaterialRepository userMaterialRepository,
      UserFavorRepository userFavorRepository) {
    this.rentalRepository = rentalRepository;
    this.rentalMapper = rentalMapper;
    this.userRepository = userRepository;
    this.billRepository = billRepository;
    this.userMaterialRepository = userMaterialRepository;
    this.userFavorRepository = userFavorRepository;
  }

  /**
   * Get a total earn between two dates start and end
   *
   * @param userId the identifier user
   * @param dateStart the date start
   * @param dateEnd the date end
   * @return an amount total
   */
  @Override
  public RentalEarnDTO totalEarned(long userId, LocalDate dateStart, LocalDate dateEnd) {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
      throw new UserNotFoundException();
    }

    Object principal = authentication.getPrincipal();

    if (!(principal instanceof UserDetails userDetails)) {
      throw new UserNotFoundException();
    }

    String pseudo = userDetails.getUsername();

    User authenticatedUser =
        userRepository.findByPseudo(pseudo).orElseThrow(UserNotFoundException::new);

    // Check if the owner is connected
    if (authenticatedUser.getId() != userId) {
      throw new UserException("Vous n'avez pas accès!");
    }

    // Check userId exists
    User ownerUser = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

    BigDecimal totalEarned = rentalRepository.totalEarned(ownerUser.getId(), dateStart, dateEnd);

    return rentalMapper.toEarnDTO(totalEarned);
  }

  /**
   * Get an amount this month
   *
   * @param userId the identifier user
   * @return a total amount earn this month
   */
  @Override
  public RentalEarnDTO totalEarnedThisMonth(long userId) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
      throw new UserNotFoundException();
    }

    Object principal = authentication.getPrincipal();

    if (!(principal instanceof UserDetails userDetails)) {
      throw new UserNotFoundException();
    }

    String pseudo = userDetails.getUsername();

    User authenticatedUser =
        userRepository.findByPseudo(pseudo).orElseThrow(UserNotFoundException::new);

    // Check if the owner is connected
    if (authenticatedUser.getId() != userId) {
      throw new UserException("Vous n'avez pas accès !");
    }

    // Check userId exists
    User ownerUser = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

    // Get the first day of the current month
    LocalDate dateStart = LocalDate.now().withDayOfMonth(1);

    // Get the first day of the next month
    LocalDate dateEnd = dateStart.plusMonths(1);

    BigDecimal totalEarned = rentalRepository.totalEarned(ownerUser.getId(), dateStart, dateEnd);

    return rentalMapper.toEarnDTO(totalEarned);
  }

  /**
   * Get a detail about the earned owner
   *
   * @param userId the identifier about user
   * @param dateStart the date start
   * @param dateEnd the date end
   * @return the detail about what the user earn
   */
  @Override
  public List<RentalDetailEarnDTO> totalDetailEarned(
      long userId, LocalDate dateStart, LocalDate dateEnd) {
    List<Rental> listRentalDetail =
        rentalRepository.findTotalDetailEarned(userId, dateStart, dateEnd);

    userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

    return rentalMapper.toDetailEarnDTO(listRentalDetail);
  }

  /**
   * Get more detail about the rental with ID
   *
   * @param id the identifier of rental
   * @return a detail more specific about the rental
   */
  @Override
  public RentalByIdDTO findRentalById(long id) {
    Rental rentalDetail = rentalRepository.findById(id).orElseThrow(RentalNotFoundException::new);

    return rentalMapper.toRentalDetailIdDTO(rentalDetail);
  }

  /**
   * Get a list of rentals not paid yet Check if the user exist
   *
   * @param userId the identifier user id
   * @return a list of rental not paid yet
   */
  @Override
  public List<RentalByIdDTO> findRentalUser(long userId) {
    userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

    List<Rental> rentalsNotPaid = rentalRepository.findRentalNotPaidYet(userId);

    return rentalMapper.toRentalListDetailIdDTO(rentalsNotPaid);
  }

  @Override
  @Transactional
  public RentalDTO createRental(long userId, RentalForm rentalForm) {
    // Check user exists
    User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

    // Check you can choose only user material or user favor but not both
    boolean hasMaterial = rentalForm.userMaterial().id() != null;
    boolean hasFavor = rentalForm.userFavor().id() != null;

    if (hasMaterial == hasFavor) {
      throw new RentalException(
          "La location doit concerner un matériel ou un service mais pas les deux");
    }

    if (!(rentalForm.dateEnd().isAfter(rentalForm.dateStart())
        || rentalForm.dateEnd().equals(rentalForm.dateStart()))) {
      throw new RentalException("La date doit être postérieure!");
    }

    // Calculate the duration between start date and time until date end and time
    LocalDateTime dateStart = LocalDateTime.of(rentalForm.dateStart(), rentalForm.startTime());
    LocalDateTime dateEnd = LocalDateTime.of(rentalForm.dateEnd(), rentalForm.endTime());

    if (!dateEnd.isAfter(dateStart)) {
      throw new RentalException("La date et l'heure de fin doivent être postérieure au début");
    }

    long hours = Duration.between(dateStart, dateEnd).toHours();

    // All time spend by user (during by owner)
    long hourPriceTotal =
        Math.max(1L, (long) Math.ceil(Duration.between(dateStart, dateEnd).toMinutes() / 60.0));

    // Retrieves the bill if the bill is not paid yet or create a new bill
    Bill bill =
        billRepository
            .findBillByUser_idAndStatus(userId, Status.PENDING)
            .orElseGet(
                () -> {
                  Bill newBill = new Bill();
                  newBill.setUser(user);
                  newBill.setStatus(Status.PENDING);
                  newBill.setIsPaid(false);
                  newBill.setAmount(BigDecimal.ZERO);

                  return billRepository.save(newBill);
                });

    // Create the rental an associate the user and the bill
    Rental rental = rentalMapper.fromRentalForm(rentalForm);
    rental.setUser(user);
    rental.setBill(bill);

    BigDecimal amount = null;

    // Retrieves the user material or user favor and calculate the price
    if (hasMaterial) {
      Long userMaterialId = rentalForm.userMaterial().id();

      UserMaterial material =
          userMaterialRepository
              .findById(userMaterialId)
              .orElseThrow(UserMaterialNotFoundException::new);

      rental.setUserMaterial(material);
      rental.setUserFavor(null);

      BigDecimal pricePerHour = material.getPriceHourMaterial();
      amount = calculateAmount(pricePerHour, hourPriceTotal);
    } else {
      Long userFavorId = rentalForm.userFavor().id();

      UserFavor favor =
          userFavorRepository.findById(userFavorId).orElseThrow(UserFavorNotFoundException::new);

      rental.setUserFavor(favor);
      rental.setUserMaterial(null);

      BigDecimal pricePerHour = favor.getPriceHourFavor();
      amount = calculateAmount(pricePerHour, hourPriceTotal);
    }

    rental.setAmount(amount);

    Rental rentalSaved = rentalRepository.save(rental);

    bill.setAmount(bill.getAmount().add(rentalSaved.getAmount()));

    return rentalMapper.toRentalDTO(rentalSaved);
  }

  /**
   * Calculates the total amount using the hourly price and the number of hours.
   *
   * @param pricePerHour the price for one hour
   * @param hourPriceTotal the total number of hours
   * @return the total amount
   * @throws RentalException if the hourly price is null or negative
   */
  private BigDecimal calculateAmount(BigDecimal pricePerHour, Long hourPriceTotal) {
    if (pricePerHour == null || pricePerHour.signum() < 0) {
      throw new RentalException("Le tarif horaire est absent ou invalide.");
    }
    return pricePerHour.multiply(BigDecimal.valueOf(hourPriceTotal));
  }

  /**
   * Update the rental
   *
   * <ul>
   *   <li>Update the date start and end
   *   <li>Update the time start and end
   *   <li>Recalculate the price amount
   *   <li>Recalculate the price bill (total)
   * </ul>
   *
   * @param id the identifier rental
   * @param form the form to update the rental
   * @return a rental updated
   */
  @Override
  @Transactional
  public RentalDTO updateRental(long id, RentalUpdateForm form) {
    // Retrieves the existing rental
    Rental rental = rentalRepository.findById(id).orElseThrow(RentalNotFoundException::new);

    // Check validates dates
    if (form.dateEnd().isBefore(form.dateStart())) {
      throw new RentalException("La date de fin doit être posterieure à la date du début ");
    }

    // Build start and end date times
    LocalDateTime dateStart = LocalDateTime.of(form.dateStart(), form.startTime());
    LocalDateTime dateEnd = LocalDateTime.of(form.dateEnd(), form.endTime());

    if (dateEnd.isBefore(dateStart)) {
      throw new RentalException("La date et l'heure de fin doivent être postérieures au début");
    }

    // Calculate new price
    long minutes = Duration.between(dateStart, dateEnd).toMinutes();
    long hours = Math.max(1L, (minutes + 59) / 60);

    // Keep the old amount to update the bill
    BigDecimal oldAmount = rental.getAmount();

    BigDecimal pricePerHour;

    // Recalculate the price the associated material or favor
    if (rental.getUserMaterial() != null) {
      pricePerHour = rental.getUserMaterial().getPriceHourMaterial();
    } else if (rental.getUserFavor() != null) {
      pricePerHour = rental.getUserFavor().getPriceHourFavor();
    } else {
      throw new RentalException("La location doit être associée à un matériel ou à un service");
    }
    BigDecimal newAmount = calculateAmount(pricePerHour, hours);

    rentalMapper.fromRentalUpdateForm(form, rental);
    rental.setStarDateAt(form.dateStart());
    rental.setEndDateAt(form.dateEnd());
    rental.setStartTime(form.startTime());
    rental.setEndTime(form.endTime());

    rental.setAmount(newAmount);

    // Update the amount in the bill
    Bill bill = rental.getBill();
    if (bill != null) {
      BigDecimal billAmount = bill.getAmount();

      bill.setAmount(billAmount.subtract(oldAmount).add(newAmount));
    }

    Rental rentalUpdate = rentalRepository.save(rental);

    return rentalMapper.toRentalDTO(rentalUpdate);
  }


  @Override
  @Transactional
  public void deleteRental(long id) {
    // Retrieve the rental
    Rental rental = rentalRepository.findById(id)
            .orElseThrow(RentalNotFoundException::new);

    // Retrieve the associated bill
    Bill bill = rental.getBill();

    // Check whether the rental is paid
    if (bill != null && Boolean.TRUE.equals(bill.getIsPaid())) {
      throw new RentalException(
              "Impossible de supprimer une location déjà payée"
      );
    }

    // Update the bill amount
    if (bill != null) {
      bill.setAmount(
              bill.getAmount().subtract(rental.getAmount())
      );
    }
    // Delete the rental
    rentalRepository.delete(rental);
  }
}
