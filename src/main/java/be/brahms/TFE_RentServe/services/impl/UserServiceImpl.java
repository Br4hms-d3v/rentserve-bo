package be.brahms.TFE_RentServe.services.impl;

import be.brahms.TFE_RentServe.exceptions.email.EmailExistingException;
import be.brahms.TFE_RentServe.exceptions.user.PseudoExistException;
import be.brahms.TFE_RentServe.exceptions.user.UserException;
import be.brahms.TFE_RentServe.mappers.AuthMapper;
import be.brahms.TFE_RentServe.models.entities.User;
import be.brahms.TFE_RentServe.models.forms.user.UserForm;
import be.brahms.TFE_RentServe.repositories.UserRepository;
import be.brahms.TFE_RentServe.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * Service implementation for managing users.
 * Uses UserRepository to perform database operations.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthMapper authMapper;

    /**
     * Constructor to create UserServiceImpl with UserRepository.
     *
     * @param userRepository        the repository to access user data
     * @param bCryptPasswordEncoder encode password with BCrypt
     * @param authMapper            the mapper for user data
     */
    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, AuthMapper authMapper) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authMapper = authMapper;
    }

    /**
     * Register a new user.
     * It checks email, pseudo, and age.
     * Password is encoded before saving.
     *
     * @param form the user form
     * @return the saved user
     */

    @Override
    public User register(UserForm form) {
        /**
         * Map the UserForm to a new User.
         * Default values are set in the mapper.
         * Some fields are ignored.
         */
        User user = authMapper.fromUserForm(form);

        // Check if exist an email
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailExistingException();
        }

        // Check if exist this pseudo
        if (userRepository.existsByPseudo(user.getPseudo())) {
            throw new PseudoExistException();
        }

        // Check if the user has been more than 18 years old
        if (!user.getBirthdate().isBefore(LocalDate.now().minusYears(18))) {
            throw new UserException("Il n'est pas possible de vous inscrire car vous êtes mineur");
        }

        // Hash the password
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
