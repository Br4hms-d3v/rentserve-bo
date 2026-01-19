package be.brahms.TFE_RentServe.exceptions.user;

/**
 * Exception evoked when user doesn't exist
 */
public class UserNotFoundException extends UserException {
    /**
     * Make a new exception when the user doesn't exist.
     *
     * @param message the error message
     */
    public UserNotFoundException(String message) {
        super(message);
    }

    /**
     * This exception is used when a user is not found
     */
    public UserNotFoundException() {
        super("L'utilisateur n'a pas été retrouvé.");
    }
}
