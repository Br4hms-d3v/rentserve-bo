package be.brahms.TFE_RentServe.exceptions.bill;

/** This is a general exception for bill errors. */
public class BillException extends RuntimeException {

  /**
   * Create a new bill exception.
   *
   * @param message the error message
   */
  public BillException(String message) {
    super(message);
  }
}
