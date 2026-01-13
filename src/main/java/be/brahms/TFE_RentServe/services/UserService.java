package be.brahms.TFE_RentServe.services;

import be.brahms.TFE_RentServe.models.entities.User;
import be.brahms.TFE_RentServe.models.forms.user.UserForm;
import jakarta.validation.Valid;

/**
 * Service interface for managing user.
 * Defines business operations related to Bill entities.
 */
public interface UserService {

    /**
     * This method saves a new user.
     *
     * @param user the user to register
     * @return the saved user
     */
    User register(@Valid UserForm user);

    /**
     * To activate the user.
     *
     * @param email The user email.
     */
    void activateUser(String email);
}
