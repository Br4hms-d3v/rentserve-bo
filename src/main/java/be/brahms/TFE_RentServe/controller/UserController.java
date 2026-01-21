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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final UserAssembler userAssembler;
    private final UserRoleAssembler userRoleAssembler;

    /**
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

    //TODO add path to Secure has many role
    @PutMapping("{id}/edit")
    public ResponseEntity<EntityModel<UserDTO>> putUserUpdate(@PathVariable long id, @RequestBody @Valid UserUpdateForm form) {

        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // System.out.println(authentication);

        //  if (authentication.getName().equals(form.pseudo())) {
        User user = userService.updateUser(id, form);
        UserDTO userUpdatedDTO = userMapper.toDto(user);
        return ResponseEntity.ok(userAssembler.toModel(userUpdatedDTO));
        //    }
        //   return ResponseEntity.badRequest().build();
    }

    @PatchMapping("{id}/change-password")
    public ResponseEntity<?> changePassword(@PathVariable long id, @RequestBody @Valid UserChangePasswordForm form) {
        User user = userService.changePassword(id, form);
        UserPasswordDTO userUpdatedDTO = userMapper.toUserPasswordDto(user);
        userAssembler.toModel1(userUpdatedDTO);

        return ResponseEntity.ok("Mot de passe changé avec succès");
    }

    @PatchMapping("{id}/delete")
    public ResponseEntity<?> deleteAccount(@PathVariable long id, @RequestBody @Valid UserDeleteForm form) {
        userService.deleteAccount(id, form);
        userAssembler.toModelDelete(id);
        return ResponseEntity.ok("Le compte a bien été supprimé");
    }
}
