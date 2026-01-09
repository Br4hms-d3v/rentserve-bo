package be.brahms.TFE_RentServe.repositories;

import be.brahms.TFE_RentServe.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
