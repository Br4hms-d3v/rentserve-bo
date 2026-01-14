package be.brahms.TFE_RentServe.repositories;

import be.brahms.TFE_RentServe.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find a user by their pseudo (username).
     *
     * @param pseudo the username to search for
     * @return an Optional containing the User if found, or empty if not
     */
    Optional<User> findByPseudo(String pseudo);

    /**
     * Check if a user exists with this email.
     *
     * @param email the email to check
     * @return true if email exists
     */
    boolean existsByEmail(String email); // Check if there is already a pseudo email.

    /**
     * Check if a user exists with this pseudo.
     *
     * @param pseudo the email to check
     * @return true if pseudo exists
     */
    boolean existsByPseudo(String pseudo); // Check if there is already a pseudo already.

    /**
     * Finds a user by email.
     *
     * @param email The email.
     * @return The user.
     */
    User findByEmail(String email);

    /**
     * Find a user by email or pseudo.
     *
     * @param email  the user's email
     * @param pseudo the user's pseudo (nickname)
     * @return an optional user if found, or empty if not found
     */
    Optional<User> findByEmailOrPseudo(String email, String pseudo);
}
