package be.brahms.TFE_RentServe.configurations.security;

import be.brahms.TFE_RentServe.models.entities.User;
import be.brahms.TFE_RentServe.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * This class loads user details from the database using the username (pseudo).
 * It is used by Spring Security to check login and roles.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    /**
     * Creates the service with the user repository.
     *
     * @param userRepository the repository to find users in the database
     */
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads a user by their pseudo (username).
     *
     * @param pseudo the user's pseudo
     * @return UserDetails for Spring Security
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String pseudo) throws UsernameNotFoundException {

        User user = userRepository.findByPseudo(pseudo).orElseThrow(() -> new UsernameNotFoundException("L'utilisateur avec le pseudo: " + pseudo + " est introuvable "));

        return new org.springframework.security.core.userdetails.User(
                user.getPseudo(),
                user.getPassword(),
                getGrantedAuthorities(user.getRole().toString())
        );

    }

    /**
     * Converts the user's role to a list of authorities for Spring Security.
     *
     * @param role the role of the user (e.g. MEMBER)
     * @return a list with one authority like "ROLE_MEMBER"
     */
    private List<GrantedAuthority> getGrantedAuthorities(String role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));

        return authorities;
    }
}
