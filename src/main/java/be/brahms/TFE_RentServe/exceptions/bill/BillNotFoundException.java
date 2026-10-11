package be.brahms.TFE_RentServe.exceptions.bill;

/** Exception evoked when the bill doesn't found. */
public class BillNotFoundException extends BillException {

  /**
   * Make a new exception when the bill doesn't found.
   *
   * @param message the error message
   */
  public BillNotFoundException(String message) {
    super(message);
  }

  /** Create a new category not found exception. Send a message for a category doesn't found */
  public BillNotFoundException() {
    super("La facture n'a pas été retrouvé .");
  }
}
