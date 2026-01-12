package be.brahms.TFE_RentServe.exceptions.email;

/**
 * Exception evoked when the email address of a user already exists.
 */
public class EmailExistingException extends EmailException {
    /**
     * Make a new exception when the email already exists.
     *
     * @param message the error message
     */
    public EmailExistingException(String message) {
        super(message);
    }

    /**
     * This exception is used when an email already exists.
     */
    public EmailExistingException() {
        super("L'email existe déjà.");
    }
}
