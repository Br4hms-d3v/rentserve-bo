package be.brahms.TFE_RentServe.exceptions.user;

/**
 * This exception is thrown when a password is invalid.
 * It is a type of {@link UserException} used to indicate that
 * an operation requiring a password has failed because the password is incorrect.
 *
 * @see UserException
 */
public class InvalidPasswordException extends RuntimeException {
    /**
     * Make a new exception when the user has a wrong password.
     *
     * @param message the error message
     */
    public InvalidPasswordException(String message) {
        super(message);
    }

    /**
     * This exception is used when the password has wrong
     */
    public InvalidPasswordException() {
        super("Votre mot de passe est erroné");
    }
}
