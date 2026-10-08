package be.brahms.TFE_RentServe.exceptions.review;

/** This is a general exception for review errors */
public class ReviewException extends RuntimeException {
  /**
   * Create a new review exception
   *
   * @param message the error message
   */
  public ReviewException(String message) {
    super(message);
  }
}
