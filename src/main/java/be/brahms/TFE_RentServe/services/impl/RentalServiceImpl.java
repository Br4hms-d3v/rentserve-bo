package be.brahms.TFE_RentServe.services.impl;

import be.brahms.TFE_RentServe.exceptions.user.UserException;
import be.brahms.TFE_RentServe.exceptions.user.UserNotFoundException;
import be.brahms.TFE_RentServe.mappers.RentalMapper;
import be.brahms.TFE_RentServe.models.dtos.rental.RentalEarnDTO;
import be.brahms.TFE_RentServe.models.entities.User;
import be.brahms.TFE_RentServe.repositories.RentalRepository;
import be.brahms.TFE_RentServe.repositories.UserRepository;
import be.brahms.TFE_RentServe.services.RentalService;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

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

  /**
   * Constructor with params
   *
   * @param rentalRepository the rentalRepo to access rental data
   * @param rentalMapper map between from Rental to entity or dto to entity
   * @param userRepository the userRepo to access user data
   */
  public RentalServiceImpl(
      RentalRepository rentalRepository, RentalMapper rentalMapper, UserRepository userRepository) {
    this.rentalRepository = rentalRepository;
    this.rentalMapper = rentalMapper;
    this.userRepository = userRepository;
  }

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
}
