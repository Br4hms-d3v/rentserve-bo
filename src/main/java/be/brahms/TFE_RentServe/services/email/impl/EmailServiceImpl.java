package be.brahms.TFE_RentServe.services.email.impl;

import be.brahms.TFE_RentServe.services.email.EmailService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * Service implementation for managing email
 * Import method EmailService
 */
@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    /**
     * Makes a new EmailServiceImpl.
     *
     * @param mailSender the mail sender.
     */
    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Sends a confirmation email.
     *
     * @param to              The email to send to.
     * @param urlConfirmation The URL.
     */
    @Override
    public void sendMailConfirmation(String to, String urlConfirmation) {
        String htmlConfirmationHtml =
                "<html>" +
                        "<body>" +
                        "<h1>RentServe</h1>" +
                        "<p>Veuillez cliquer sur le lien ci-dessous pour activer votre compte</p>" +
                        "<a href=\"" + urlConfirmation + "\">Confirmer mon inscription</a>" +
                        "</body>" +
                        "</html>";

        // Create a new email message It will be used to send a confirmation email.
        MimeMessage messageConfirmation = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(messageConfirmation, true);
            helper.setTo(to);
            helper.setSubject("Confirmation d'inscription");
            helper.setText(htmlConfirmationHtml, true);

            mailSender.send(messageConfirmation);
        } catch (Exception e) {
            System.out.println("Error to send the mail: " + e.getMessage());
        }
    }
}
