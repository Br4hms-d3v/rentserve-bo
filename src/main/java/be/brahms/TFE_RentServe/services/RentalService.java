package be.brahms.TFE_RentServe.services;

import be.brahms.TFE_RentServe.models.dtos.rental.RentalByIdDTO;
import be.brahms.TFE_RentServe.models.dtos.rental.RentalDetailEarnDTO;
import be.brahms.TFE_RentServe.models.dtos.rental.RentalEarnDTO;
import java.time.LocalDate;
import java.util.List;

/** Service interface for managing rental. Defines business operations related to rental entity */
public interface RentalService {

  /**
   * This method get the amount earned on owner Rental
   *
   * @param userId the identifier user
   * @param dateStart the date start
   * @param dateEnd the date end
   * @return the amount earned
   */
  RentalEarnDTO totalEarned(long userId, LocalDate dateStart, LocalDate dateEnd);

  /**
   * This method get amount only this month
   *
   * @param userId the identifier user
   * @return the amount earn this month
   */
  RentalEarnDTO totalEarnedThisMonth(long userId);

  /**
   * Get a detail about each earn
   *
   * @param userId the identifier about user
   * @param dateStart the date start
   * @param dateEnd the date end
   * @return the list of details about amount earned
   */
  List<RentalDetailEarnDTO> totalDetailEarned(long userId, LocalDate dateStart, LocalDate dateEnd);

  /**
   * Get a detail about the rental
   *
   * @param id the identifier of rental
   * @return the detail about the rental
   */
  RentalByIdDTO findRentalById(long id);
}
