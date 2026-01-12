package be.brahms.TFE_RentServe.exceptions.user;

/**
 * Exception evoked when the pseudo of a user already exists.
 */
public class PseudoExistException extends UserException {
    /**
     * Make a new exception when the pseudo already exists.
     *
     * @param message the error message
     */
    public PseudoExistException(String message) {
        super(message);

    }

    /**
     * This exception is used when a pseudo already exists.
     */
    public PseudoExistException() {
        super("Ce pseudo est déjà utilisé.");
    }
}
