package be.brahms.TFE_RentServe.services;

import be.brahms.TFE_RentServe.enums.Role;
import be.brahms.TFE_RentServe.models.entities.User;
import be.brahms.TFE_RentServe.models.forms.user.UserForm;
import be.brahms.TFE_RentServe.models.forms.user.UserLoginForm;
import be.brahms.TFE_RentServe.models.forms.user.UserUpdateForm;
import jakarta.validation.Valid;

import java.util.List;

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

    /**
     * This method connect the user
     *
     * @param user the user for log in
     * @return the connected user
     */
    User login(UserLoginForm user);

    /**
     * This method get a user by ID
     *
     * @param id from the user
     * @return data's from user by id if exist
     */
    User userFindById(long id);

    /**
     * This method get a list of users
     *
     * @return list of all users
     */
    List<User> findAllUsers();

    /**
     * Get all users with a specific role.
     *
     * @param role the role to filter users
     * @return list of users with the given role
     */
    List<User> findUsersByRole(Role role);

    /**
     * Update the user
     *
     * @param id   the user's ID
     * @param user the data user
     * @return the new user
     */
    User updateUser(long id, UserUpdateForm user);
}
