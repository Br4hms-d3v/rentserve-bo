package be.brahms.TFE_RentServe.exceptions.user;

/**
 * Exception evoked when the pseudo from user doesn't exist.
 */
public class PseudoNotFoundException extends UserException {
    /**
     * Make a new exception when the pseudo doesn't exist.
     *
     * @param message the error message
     */
    public PseudoNotFoundException(String message) {
        super(message);
    }

    /**
     * Create a new pseudo not found exception.
     * <p>
     * Send a message for a pseudo doesn't found
     */
    public PseudoNotFoundException() {
        super("Le pseudo n'existe pas ou n'a pas été retrouvé");
    }
}
