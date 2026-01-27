package be.brahms.TFE_RentServe.controller;

import be.brahms.TFE_RentServe.enums.Role;
import be.brahms.TFE_RentServe.hateoas.user.UserAssembler;
import be.brahms.TFE_RentServe.hateoas.user.UserRoleAssembler;
import be.brahms.TFE_RentServe.mappers.UserMapper;
import be.brahms.TFE_RentServe.models.dtos.user.UserDTO;
import be.brahms.TFE_RentServe.models.dtos.user.UserPasswordDTO;
import be.brahms.TFE_RentServe.models.dtos.user.UserRoleDTO;
import be.brahms.TFE_RentServe.models.entities.User;
import be.brahms.TFE_RentServe.models.forms.user.UserChangePasswordForm;
import be.brahms.TFE_RentServe.models.forms.user.UserDeleteForm;
import be.brahms.TFE_RentServe.models.forms.user.UserUpdateForm;
import be.brahms.TFE_RentServe.services.UserService;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This controller manages User
 * It has a method to display user by id
 * It has a method to display a list of all users
 * It has a method to display a list of users by their role
 * It has a method to update a user by his is
 * It has a method to change only the password
 * It has a method to delete an account
 *
 * @author Brahim K
 */
@RestController
@RequestMapping("/api/user/")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final UserAssembler userAssembler;
    private final UserRoleAssembler userRoleAssembler;

    /**
     * Constructor with params
     * It's require 4 dependencies:
     * - user service
     * - user mapper
     * - user and user role Assembler
     *
     * @param userService       service for user management
     * @param userMapper        mapper entity to Dto or form to entity
     * @param userAssembler     hateoas create the link to redirect to endPoints
     * @param userRoleAssembler hateoas create the link to redirect to user role
     */
    public UserController(UserService userService, UserMapper userMapper, UserAssembler userAssembler, UserRoleAssembler userRoleAssembler) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.userAssembler = userAssembler;
        this.userRoleAssembler = userRoleAssembler;
    }

    /**
     * Get user's data by his ID
     *
     * @param id identifier unique
     * @return data's about the user
     */
    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('MEMBER','MODERATOR','ADMIN')")
    public ResponseEntity<EntityModel<UserDTO>> getUser(@PathVariable long id) {
        User userById = userService.userFindById(id);
        UserDTO userDto = userMapper.toDto(userById);

        return ResponseEntity.ok(userAssembler.toModel(userDto));
    }

    /**
     * Get a list of all users
     *
     * @return a list of all users
     */
    @GetMapping("list")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CollectionModel<UserDTO>> getAllUsers() {
        List<User> users = userService.findAllUsers();
        List<UserDTO> listUsersDto = users
                .stream()
                .map(userMapper::toDto)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(listUsersDto));
    }

    /**
     * Get a list of user only by their role
     *
     * @param role Admin, Moderator or Member
     * @return a list of user only with the role selected
     */
    @GetMapping("list/{role}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CollectionModel<EntityModel<UserRoleDTO>>> getUsersByRole(@PathVariable Role role) {
        List<UserRoleDTO> usersRoleDTO = userService.findUsersByRole(role)
                .stream()
                .map(userMapper::listRoleToDto)
                .toList();

        List<EntityModel<UserRoleDTO>> usersRoleListToModel = usersRoleDTO
                .stream()
                .map(userRoleAssembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(usersRoleListToModel));
    }

    /**
     * This method update some data's about the user
     * Check the id, email and authenticate before update
     *
     * @param id   the identifier of user
     * @param form the form with new data to update
     * @return a new response with data updated
     */
    @PutMapping("{id}/edit")
    @PreAuthorize("hasAnyRole('MEMBER','MODERATOR','ADMIN')")
    public ResponseEntity<EntityModel<UserDTO>> putUserUpdate(@PathVariable long id, @RequestBody @Valid UserUpdateForm form) {
        User user = userService.updateUser(id, form);
        UserDTO userUpdatedDTO = userMapper.toDto(user);
        return ResponseEntity.ok(userAssembler.toModel(userUpdatedDTO));

    }

    /**
     * Change only the password about the user
     * Check his email and his password before to change
     *
     * @param id   the identifier of user
     * @param form the form with new password updated
     * @return a message to confirm his password has been changed
     */
    @PatchMapping("{id}/change-password")
    @PreAuthorize("hasAnyRole('MEMBER','MODERATOR','ADMIN')")
    public ResponseEntity<?> changePassword(@PathVariable long id, @RequestBody @Valid UserChangePasswordForm form) {
        User user = userService.changePassword(id, form);
        UserPasswordDTO userUpdatedDTO = userMapper.toUserPasswordDto(user);
        userAssembler.toModel1(userUpdatedDTO);

        return ResponseEntity.ok("Mot de passe changé avec succès");
    }

    /**
     * Delete remove the user on the app
     * Before to delete, the user must confirm with his email
     * Soft delete => change is active to is not activate
     *
     * @param id   the identifier of user
     * @param form the form to confirm delete
     * @return a message to confirm his account is deactivated
     */
    @PatchMapping("{id}/delete")
    @PreAuthorize("hasAnyRole('MEMBER','MODERATOR','ADMIN')")
    public ResponseEntity<?> deleteAccount(@PathVariable long id, @RequestBody @Valid UserDeleteForm form) {
        userService.deleteAccount(id, form);
        userAssembler.toModelDelete(id);
        return ResponseEntity.ok("Le compte a bien été supprimé");
    }
}
