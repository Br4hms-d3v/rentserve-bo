package be.brahms.TFE_RentServe.exceptions.database;

/**
 * This is a general exception for Database Constrain To delete a table with foreign key errors.
 */
public class DatabaseConstraintException extends RuntimeException {
    /**
     * Create an exception with a message.
     * @param message the error message
     */
    public DatabaseConstraintException(String message) {
        super(message);
    }

    /**
     * Create an exception with the message and the original error.
     * @param message the error message
     * @param cause the original exception
     */
    public DatabaseConstraintException(String message, Throwable cause) {
        super(message, cause);
    }
}
