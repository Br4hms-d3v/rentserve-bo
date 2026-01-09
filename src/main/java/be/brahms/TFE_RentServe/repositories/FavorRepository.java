package be.brahms.TFE_RentServe.repositories;

import be.brahms.TFE_RentServe.models.entities.Favor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavorRepository extends JpaRepository<Favor, Long> {
}
