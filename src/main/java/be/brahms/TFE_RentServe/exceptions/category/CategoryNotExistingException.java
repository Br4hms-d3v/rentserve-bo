package be.brahms.TFE_RentServe.exceptions.category;

/**
 * Exception evoked when the name of category not exists.
 */
public class CategoryNotExistingException extends CategoryException {
    /**
     * Make a new exception when the name of category not exists.
     *
     * @param message the error message
     */
    public CategoryNotExistingException(String message) {
        super(message);
    }

    /**
     * This exception is used when a name of category doesn't exist.
     */
    public CategoryNotExistingException() {
        super("The name of category not exists");
    }
}
