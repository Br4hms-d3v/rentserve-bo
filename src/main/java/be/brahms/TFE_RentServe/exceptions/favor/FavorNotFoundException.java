package be.brahms.TFE_RentServe.exceptions.favor;

/**
 * Exception evoked when the favor not found
 */
public class FavorNotFoundException extends FavorException {
    /**
     * Make a new exception when the favor is not found.
     *
     * @param message the error message
     */
    public FavorNotFoundException(String message) {
        super(message);
    }

    /**
     * This exception is used when the favor is not founded
     */
    public FavorNotFoundException() {
        super("The favor is not founded");
    }
}
