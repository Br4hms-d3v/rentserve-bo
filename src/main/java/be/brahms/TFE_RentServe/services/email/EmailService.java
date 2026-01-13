package be.brahms.TFE_RentServe.services.email;

public interface EmailService {

    /**
     * Sends a mail to confirm.
     *
     * @param to              The email.
     * @param urlConfirmation The URL.
     */
    void sendMailConfirmation(String to, String urlConfirmation);
}
