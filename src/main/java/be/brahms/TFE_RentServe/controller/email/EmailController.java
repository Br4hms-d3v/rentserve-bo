package be.brahms.TFE_RentServe.controller.email;

import be.brahms.TFE_RentServe.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller manages email.
 * It has method to send an email.
 */
@RestController
@RequestMapping("/api/mail")
public class EmailController {

    private final UserService userService;

    /**
     * Constructor for email
     *
     * @param userService the user service
     */
    public EmailController(UserService userService) {
        this.userService = userService;
    }

    /**
     * receive a request to activate the account
     *
     * @param email the user e-mail
     * @return a message on webpage the account is activated
     */
    @GetMapping("/confirmation")
    public ResponseEntity<String> mailConfirmation(@RequestParam String email) {
        userService.activateUser(email);

        return ResponseEntity.ok("Account activated successfully");
    }


}
