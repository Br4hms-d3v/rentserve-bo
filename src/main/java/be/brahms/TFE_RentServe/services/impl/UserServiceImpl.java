package be.brahms.TFE_RentServe.services.impl;

import be.brahms.TFE_RentServe.exceptions.email.EmailExistingException;
import be.brahms.TFE_RentServe.exceptions.email.EmailNotFoundException;
import be.brahms.TFE_RentServe.exceptions.user.*;
import be.brahms.TFE_RentServe.mappers.AuthMapper;
import be.brahms.TFE_RentServe.mappers.UserMapper;
import be.brahms.TFE_RentServe.models.dtos.email.EmailTokenDTO;
import be.brahms.TFE_RentServe.models.entities.User;
import be.brahms.TFE_RentServe.models.forms.user.UserForm;
import be.brahms.TFE_RentServe.models.forms.user.UserLoginForm;
import be.brahms.TFE_RentServe.repositories.UserRepository;
import be.brahms.TFE_RentServe.services.UserService;
import be.brahms.TFE_RentServe.services.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service implementation for managing users.
 * Uses UserRepository to perform database operations.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthMapper authMapper;
    private final UserMapper userMapper;
    private final EmailService emailService;

    /**
     * Constructor to create UserServiceImpl with UserRepository.
     *
     * @param userRepository        the repository to access user data
     * @param bCryptPasswordEncoder encode password with BCrypt
     * @param authMapper            the mapper for user data
     */
    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, AuthMapper authMapper, UserMapper userMapper, EmailService emailService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authMapper = authMapper;
        this.userMapper = userMapper;
        this.emailService = emailService;
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

        // Generate a token for email
        EmailTokenDTO emailTokenDTO = new EmailTokenDTO();

        // Send email for confirmation of registration account
        String confirmation = "http://localhost:8080/api/mail/confirmation?token=" + emailTokenDTO.confirmationToken() + "&email=" + user.getEmail();

        emailService.sendMailConfirmation(user.getEmail(), confirmation);
        return userRepository.save(user);
    }

    /**
     * This method activate the user
     * after click on his email.
     * The account come True
     *
     * @param email The user email.
     */
    @Override
    public void activateUser(String email) {
        User userActivated = userRepository.findByEmail(email);

        if (userActivated == null) {
            throw new EmailNotFoundException();
        }

        userActivated.setIsActive(true);
        userRepository.save(userActivated);
    }

    @Override
    public User login(UserLoginForm form) {
        Optional<User> foundEmailOrPseudo = userRepository.findByEmailOrPseudo(form.email(), form.pseudo());

        /**
         * Check the email or pseudo
         * If it doesn't exist in the DB
         * Send Exception
         */
        if (foundEmailOrPseudo.isEmpty()) {

            boolean userLoginWithEmail = userRepository.existsByEmail(form.email());
            boolean userLoginWithPseudo = userRepository.existsByPseudo(form.pseudo());

            // Check if the email or pseudo are available in the DB
            if (!userLoginWithEmail && form.email() != null) {
                throw new EmailNotFoundException();
            }
            if (!userLoginWithPseudo && form.pseudo() != null) {
                throw new PseudoNotFoundException();
            }
        }

        /**
         * Check the account boolean
         * If it's true the account is available
         * If it's false the account isn't available
         */
        User user = foundEmailOrPseudo.get();

        if (!user.getIsActive()) {
            throw new AccountNotActivatedException();
        }

        // Check the password between DB and the user
        if (!bCryptPasswordEncoder.matches(form.password(), user.getPassword())) {
            throw new InvalidPasswordException();
        }

        return user;
    }

    /**
     * Get user by ID
     * If not found send a message exception
     *
     * @param id from the user
     * @return data about user
     */
    @Override
    public User userFindById(long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    /**
     * Get a list of all users
     * If the list is empty, send a message
     *
     * @return a list of all users
     */
    @Override
    public List<User> findAllUsers() {
        List<User> listOfUsers = userRepository.findAll();

        // If the list is empty, send a message exception
        if (listOfUsers.isEmpty()) {
            throw new UserException("La liste est vide !");
        }

        return listOfUsers;
    }
}
