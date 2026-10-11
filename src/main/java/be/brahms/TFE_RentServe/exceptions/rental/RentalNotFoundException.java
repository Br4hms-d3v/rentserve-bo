package be.brahms.TFE_RentServe.exceptions.rental;

/** Exception evoked when the rental not founded */
public class RentalNotFoundException extends RentalException {
  /**
   * Make a new exception when the review not exists.
   *
   * @param message the error message
   */
  public RentalNotFoundException(String message) {
    super(message);
  }

  /** This exception is used when a rental was not founded */
  public RentalNotFoundException() {
    super("La location n'a pas été retrouvée !");
  }
}
