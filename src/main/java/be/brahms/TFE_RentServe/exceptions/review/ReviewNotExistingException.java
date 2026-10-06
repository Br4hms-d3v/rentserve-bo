package be.brahms.TFE_RentServe.exceptions.review;

/** Exception evoked when the review not exists */
public class ReviewNotExistingException extends RuntimeException {

    /**
     * Make a new exception when the review not exists.
     *
     * @param message the error message
     */
    public ReviewNotExistingException(String message) {
        super(message);
    }

    /** This exception is used when a review doesn't exist */
    public ReviewNotExistingException() {
        super();
    }
}
