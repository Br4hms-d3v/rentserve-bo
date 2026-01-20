package be.brahms.TFE_RentServe.exceptions.email;

public class EmailNotFoundException extends EmailException {
    public EmailNotFoundException(String message) {
        super(message);
    }

    public EmailNotFoundException() {
        super("L'email n'a pas été retrouvé");
    }
}
