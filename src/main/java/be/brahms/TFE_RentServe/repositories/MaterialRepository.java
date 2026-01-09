package be.brahms.TFE_RentServe.repositories;

import be.brahms.TFE_RentServe.models.entities.Material;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends JpaRepository<Material, Long> {
}
