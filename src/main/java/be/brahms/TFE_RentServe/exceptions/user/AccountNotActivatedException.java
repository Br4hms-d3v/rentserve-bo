package be.brahms.TFE_RentServe.exceptions.user;

/**
 * Exception evoked when the account user is not activated yet.
 */
public class AccountNotActivatedException extends UserException {
    /**
     * Make a new exception when the user has not yet activated the account.
     *
     * @param message the error message
     */
    public AccountNotActivatedException(String message) {
        super(message);
    }

    /**
     * This exception is used when the account has not been activated
     */
    public AccountNotActivatedException() {
        super("Ce compte n'a pas encore été activé pour le moment!");
    }
}
