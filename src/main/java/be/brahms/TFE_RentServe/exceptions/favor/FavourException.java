package be.brahms.TFE_RentServe.exceptions.favor;

/**
 * This is a general exception for favor errors.
 */
public class FavourException extends RuntimeException {
    /**
     * Create a new favor exception.
     *
     * @param message the error message
     */
    public FavourException(String message) {
        super(message);
    }
}
