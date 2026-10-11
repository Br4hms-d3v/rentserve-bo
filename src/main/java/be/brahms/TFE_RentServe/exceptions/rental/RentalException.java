package be.brahms.TFE_RentServe.exceptions.rental;

/** This is a general exception for review exception review errors */
public class RentalException extends RuntimeException {
  /**
   * Create a new rental exception
   *
   * @param message the error message
   */
  public RentalException(String message) {
    super(message);
  }
}
