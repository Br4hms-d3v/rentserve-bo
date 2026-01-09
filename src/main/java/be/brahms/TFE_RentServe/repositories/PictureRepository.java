package be.brahms.TFE_RentServe.repositories;

import be.brahms.TFE_RentServe.models.entities.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRepository extends JpaRepository<Picture, Long> {
}
