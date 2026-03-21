package be.brahms.TFE_RentServe.exceptions.material;

/**
 * This is a general exception for errors.
 */
public class MaterialException extends RuntimeException {
    /**
     * Create a new material exception
     *
     * @param message the error message
     */
    public MaterialException(String message) {
        super(message);
    }
}
