package be.brahms.TFE_RentServe.services.impl;

import be.brahms.TFE_RentServe.enums.Role;
import be.brahms.TFE_RentServe.exceptions.email.EmailException;
import be.brahms.TFE_RentServe.exceptions.email.EmailExistingException;
import be.brahms.TFE_RentServe.exceptions.email.EmailNotFoundException;
import be.brahms.TFE_RentServe.exceptions.user.*;
import be.brahms.TFE_RentServe.mappers.AuthMapper;
import be.brahms.TFE_RentServe.mappers.UserMapper;
import be.brahms.TFE_RentServe.models.dtos.email.EmailTokenDTO;
import be.brahms.TFE_RentServe.models.dtos.user.UserDTO;
import be.brahms.TFE_RentServe.models.dtos.user.UserPasswordDTO;
import be.brahms.TFE_RentServe.models.dtos.user.UserRoleDTO;
import be.brahms.TFE_RentServe.models.entities.User;
import be.brahms.TFE_RentServe.models.forms.user.*;
import be.brahms.TFE_RentServe.repositories.UserRepository;
import be.brahms.TFE_RentServe.services.UserService;
import be.brahms.TFE_RentServe.services.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
     * @param bCryptPasswordEncoder encode password with Bcrypt
     * @param authMapper            map between form auth to entity
     * @param userMapper            map between form user to entity
     * @param emailService          email confirm or update password
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

        //Map the UserForm to a new User.
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

    /**
     * This method connect to the app
     *
     * @param form the user for log in
     * @return a user connected with
     */
    @Override
    public User login(UserLoginForm form) {
        Optional<User> foundEmailOrPseudo = userRepository.findByEmailOrPseudo(form.email(), form.pseudo());


        // If it doesn't exist in the DB
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


        // Check the account boolean
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
    public UserDTO userFindById(long id) {
        String currentPseudo = SecurityContextHolder.getContext().getAuthentication().getName();

        User currentUser = userRepository.findByPseudo(currentPseudo).orElseThrow(UserNotFoundException::new);

        if (currentUser.getId() != id) {
            throw new AccessNotAuthorizedException();
        }
        return userMapper.toDto(currentUser);

    }

    /**
     * Get a list of all users
     * If the list is empty, send a message
     *
     * @return a list of all users
     */
    @Override
    public List<UserDTO> findAllUsers() {
        List<User> listOfUsers = userRepository.findAll();

        // If the list is empty, send a message exception
        if (listOfUsers.isEmpty()) {
            throw new UserException("La liste est vide !");
        }

        return listOfUsers
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    /**
     * Get a list of all users by their role
     *
     * @param role The role of user (Member, Moderator or Admin)
     * @return a list of users
     */
    @Override
    public List<UserRoleDTO> findUsersByRole(Role role) {
        List<User> listUserByRole = userRepository.listUsersByRole(role);

        if (listUserByRole.isEmpty()) {
            throw new UserException("Aucun n'a le role: " + role);
        }

        return listUserByRole
                .stream()
                .map(userMapper::listRoleToDto)
                .toList();
    }

    /**
     * Update the profile user
     * Check if the pseudo, email exist before save
     *
     * @param id   the user's ID
     * @param user the data user
     * @return a user with data updated
     */
    public UserDTO updateUser(long id, UserUpdateForm user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userIdUpdate = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        boolean userEmailExist = userRepository.existsByEmail(user.email());
        boolean userPseudoExist = userRepository.existsByPseudo(user.pseudo());

        if (!authentication.getName().equals(userIdUpdate.getPseudo())) {
            throw new AccessNotAuthorizedException();
        }
        if (userPseudoExist && !userIdUpdate.getPseudo().equals(user.pseudo())) {
            throw new PseudoExistException();
        }
        if (userEmailExist && !userIdUpdate.getEmail().equals(user.email())) {
            throw new EmailExistingException();
        }

        // Persist new data to old data
        userIdUpdate.setPseudo(user.pseudo());
        userIdUpdate.setEmail(user.email());

        userIdUpdate.setIsActive(true);
        userIdUpdate.setStreet(user.street());
        userIdUpdate.setCity(user.city());
        userIdUpdate.setZipCode(user.zipCode());

        // map Form to Entity
        userMapper.fromUpdateUserForm(user, userIdUpdate);

        User savedUser = userRepository.save(userIdUpdate);

        // Entity to Dto
        return userMapper.toDto(savedUser);
    }

    @Override
    public UserPasswordDTO changePassword(long id, UserChangePasswordForm user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userUpdatePassword = userRepository.findByEmail(user.email());
        User userId = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        // Check if the user who use the form is the same as the identifier about the user
        if (!authentication.getName().equals(userId.getPseudo())) {
            throw new AccessNotAuthorizedException();
        }
        // Check the email exist or not => send a message with exception
        if (userUpdatePassword.getEmail() == null) {
            throw new EmailNotFoundException();
        }
        if (!user.email().equals(userUpdatePassword.getEmail())) {
            throw new EmailException("Votre email n'est pas identique à votre compte");
        }
        if (!user.password().equals(user.comparePassword())) {
            throw new InvalidPasswordException("Le mot de passe doit être identique");
        }
        if (user.email().isEmpty() || user.email().isBlank()) {
            throw new EmailException("Veuillez entrez une adresse email");
        }
        userMapper.fromUserChangePasswordForm(user, userUpdatePassword);
        // Hash the password
        userUpdatePassword.setPassword(bCryptPasswordEncoder.encode(user.password()));

        userRepository.save(userUpdatePassword);
        // Generate a token for email
        EmailTokenDTO emailTokenDTO = new EmailTokenDTO();

        String warnUpdatePasswordConfirmationUrl = "http://localhost:8080/api/user/" + id + "/change-password?token=" + emailTokenDTO.confirmationToken() + "&email=" + user.email();

        emailService.sendEmailUpdatePassword(user.email(), warnUpdatePasswordConfirmationUrl);

        return userMapper.toUserPasswordDto(userUpdatePassword);
    }

    /**
     * Soft delete of user
     *
     * @param id   the identifier of user
     * @param form the form to delete the account
     */
    @Override
    public void deleteAccount(long id, UserDeleteForm form) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Get user id from form delete
        User userId = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        if (!authentication.getName().equals(userId.getPseudo())) {
            throw new AccessNotAuthorizedException();
        }
        // Check if it's already deactivated
        if (!userId.getIsActive()) {
            throw new UserException("Le compte est déjà supprimé");
        }
        // Check if it's email is identical
        if (!userId.getEmail().equals(form.email())) {
            throw new EmailException("L'email fourni est incorrect");
        }
        // Compare the password
        if (!bCryptPasswordEncoder.matches(form.password(), userId.getPassword())) {
            throw new InvalidPasswordException();
        }
        // Map the form
        userMapper.fromDeleteForm(form, userId);
        // Persist to DB
        userId.setIsActive(false);

        userRepository.deleteAccount(id);

    }


}
