package be.brahms.TFE_RentServe.repositories;

import be.brahms.TFE_RentServe.enums.Role;
import be.brahms.TFE_RentServe.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Repository for managing User entities.
 * Provides basic CRUD operations and more using JpaRepository.
 */
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

    /**
     * Find users by their role.
     *
     * @param role the role to search for
     * @return list of users with the given role
     */
    @Query("SELECT u FROM User u WHERE u.role = :role")
    List<User> listUsersByRole(@Param("role") Role role);

    /**
     * Delete an account user
     * It is change the boolean from true to false
     *
     * @param id the identifier of user
     */
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.isActive = false WHERE u.id = :id")
    void deleteAccount(@Param("id") Long id);
}
