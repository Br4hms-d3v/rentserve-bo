package be.brahms.TFE_RentServe.repositories;

import be.brahms.TFE_RentServe.models.entities.UserMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMaterialRepository extends JpaRepository<UserMaterial, Long> {
}
