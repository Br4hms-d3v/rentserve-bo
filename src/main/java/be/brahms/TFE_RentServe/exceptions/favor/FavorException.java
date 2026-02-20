package be.brahms.TFE_RentServe.exceptions.favor;

/**
 * This is a general exception for favor errors.
 */
public class FavorException extends RuntimeException {
    /**
     * Create a new favor exception.
     *
     * @param message the error message
     */
    public FavorException(String message) {
        super(message);
    }
}
