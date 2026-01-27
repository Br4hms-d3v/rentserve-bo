package be.brahms.TFE_RentServe.exceptions.email;

/**
 * Exception evoked when the email was not found
 */
public class EmailNotFoundException extends EmailException {
    /**
     * Make a new exception when the user entre an email but not found
     *
     * @param message the error message
     */
    public EmailNotFoundException(String message) {
        super(message);
    }

    /**
     * This exception is used when the user enter an email but doesn't exist
     */
    public EmailNotFoundException() {
        super("L'email n'a pas été retrouvé");
    }
}
