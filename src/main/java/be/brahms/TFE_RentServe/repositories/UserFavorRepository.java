package be.brahms.TFE_RentServe.repositories;

import be.brahms.TFE_RentServe.models.entities.UserFavor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFavorRepository extends JpaRepository<UserFavor, Long> {
}
