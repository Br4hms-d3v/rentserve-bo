package be.brahms.TFE_RentServe.exceptions.user;

public class AccessNotAuthorizedException extends RuntimeException {
    public AccessNotAuthorizedException(String message) {
        super(message);
    }

    public AccessNotAuthorizedException() {
        super("Vous n'avez pas le droit !");
    }
}
