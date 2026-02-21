package be.brahms.TFE_RentServe.exceptions.favor;

/**
 * Exception evoked when the favor already exists.
 */
public class FavorAlreadyExistingException extends FavorException {
    /**
     * This exception is used when a category already exists.
     */
    public FavorAlreadyExistingException() {
        super("Le service existe déjà");
    }
}
