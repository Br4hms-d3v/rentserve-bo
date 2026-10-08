package be.brahms.TFE_RentServe.services;

import be.brahms.TFE_RentServe.models.dtos.rental.RentalEarnDTO;
import java.time.LocalDate;

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
}
