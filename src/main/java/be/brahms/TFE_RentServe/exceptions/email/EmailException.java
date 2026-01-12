package be.brahms.TFE_RentServe.exceptions.email;

/**
 * This is a general exception for email errors
 */
public class EmailException extends RuntimeException {
    /**
     * Create a new email exception.
     *
     * @param message the error message
     */
    public EmailException(String message) {
        super(message);
    }
}
