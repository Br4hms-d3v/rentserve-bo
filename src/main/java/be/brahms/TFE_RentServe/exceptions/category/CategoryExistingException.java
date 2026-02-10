package be.brahms.TFE_RentServe.exceptions.category;

/**
 * Exception evoked when the name of category already exists.
 */
public class CategoryExistingException extends CategoryException {
    /**
     * Make a new exception when the name of category already exists.
     *
     * @param message the error message
     */
    public CategoryExistingException(String message) {
        super(message);
    }

    /**
     * This exception is used when a name of category already exists.
     */
    public CategoryExistingException() {
        super("La categorie existe déjà");
    }
}
