package be.brahms.TFE_RentServe.exceptions.favor;

/**
 * Exception evoked when the favor is empty.
 */
public class FavorNotEmptyException extends FavorException {
    /**
     * Make a new exception when the favor is empty.
     *
     * @param message the error message
     */
    public FavorNotEmptyException(String message) {
        super(message);
    }

    /**
     * Create a new favor is empty exception.
     * Send a message for a favor is empty
     */
    public FavorNotEmptyException() {
        super("The name of favor must no be empty");
    }
}
