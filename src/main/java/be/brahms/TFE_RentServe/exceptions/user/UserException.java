package be.brahms.TFE_RentServe.exceptions.user;

/**
 * This is a general exception for user errors.
 */
public class UserException extends RuntimeException {
    /**
     * Create a new user exception.
     *
     * @param message the error message
     */
    public UserException(String message) {
        super(message);
    }
}
