package be.brahms.TFE_RentServe.controller;

import be.brahms.TFE_RentServe.hateoas.user.UserAssembler;
import be.brahms.TFE_RentServe.mappers.UserMapper;
import be.brahms.TFE_RentServe.models.dtos.user.UserDTO;
import be.brahms.TFE_RentServe.models.entities.User;
import be.brahms.TFE_RentServe.services.UserService;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final UserAssembler userAssembler;

    /**
     * UserController constructor
     *
     * @param userService service for user management
     * @param userMapper  mapper entity to Dto or form to entity
     */
    public UserController(UserService userService, UserMapper userMapper, UserAssembler userAssembler) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.userAssembler = userAssembler;
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
}
