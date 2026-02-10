package be.brahms.TFE_RentServe.exceptions.category;

/**
 * Exception evoked when the category doesn't exist.
 */
public class CategoryNotFoundException extends CategoryException {
    /**
     * Make a new exception when the category doesn't exist.
     *
     * @param message the error message
     */
    public CategoryNotFoundException(String message) {
        super(message);
    }

    /**
     * Create a new category not found exception.
     * Send a message for a category doesn't found
     */
    public CategoryNotFoundException() {
        super("La categorie na pas été retrouvé");
    }
}
