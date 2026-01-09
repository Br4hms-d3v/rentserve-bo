package be.brahms.TFE_RentServe.repositories;

import be.brahms.TFE_RentServe.models.entities.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, Long> {
}
