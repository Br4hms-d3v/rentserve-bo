package be.brahms.TFE_RentServe.controller;

import be.brahms.TFE_RentServe.mappers.AuthMapper;
import be.brahms.TFE_RentServe.models.dtos.user.UserTokenDTO;
import be.brahms.TFE_RentServe.models.entities.User;
import be.brahms.TFE_RentServe.models.forms.user.UserForm;
import be.brahms.TFE_RentServe.services.UserService;
import be.brahms.TFE_RentServe.utilities.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller manages authentication.
 * It has a method to register a new user.
 * It has a method to sign in a user.
 */
@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthMapper authMapper;

    /**
     * AuthController constructor.
     *
     * @param userService service for user management
     * @param jwtUtil     utility for managing JWT tokens
     * @param authMapper  mapper entity to Dto
     */
    public AuthController(UserService userService, JwtUtil jwtUtil, AuthMapper authMapper) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.authMapper = authMapper;
    }

    /**
     * This method registers a new user.
     * It makes a token and sends back user info with the token.
     *
     * @param form the form with user data
     * @return user info and token
     */
    @PostMapping("/register")
    public UserTokenDTO register(@RequestBody @Valid UserForm form) {
        User user = userService.register(form);
        String token = jwtUtil.generateToken(user);
        return authMapper.toTokenDTO(user, token);
    }
}

