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
}
