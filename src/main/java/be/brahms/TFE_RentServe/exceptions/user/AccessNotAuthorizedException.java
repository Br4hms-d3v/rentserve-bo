package be.brahms.TFE_RentServe.exceptions.user;

/**
 * Exception evoked when the user can access.
 */
public class AccessNotAuthorizedException extends UserException {
    /**
     * Make a new exception when the user try access a data from another
     *
     * @param message the error message
     */
    public AccessNotAuthorizedException(String message) {
        super(message);
    }

    /**
     * This exception is used when the user try to access data about the another user
     */
    public AccessNotAuthorizedException() {
        super("Vous n'avez pas le droit !");
    }
}
