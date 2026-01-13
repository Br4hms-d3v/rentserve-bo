package be.brahms.TFE_RentServe.exceptions.email;

public class EmailNotFoundException extends RuntimeException {
    public EmailNotFoundException(String message) {
        super(message);
    }

    public EmailNotFoundException() {
        super("L'email n'a pas été retrouvé");
    }
}
