package be.brahms.TFE_RentServe.exceptions.category;

/**
 * Exception evoked when the category is empty.
 */
public class CategoryNotEmptyException extends CategoryException {
    /**
     * Make a new exception when the category is empty.
     *
     * @param message the error message
     */
    public CategoryNotEmptyException(String message) {
        super(message);
    }

    /**
     * Create a new category not found exception.
     * Send a message for a category doesn't found
     */
    public CategoryNotEmptyException() {
        super("The name of category must not be empty");
    }
}
