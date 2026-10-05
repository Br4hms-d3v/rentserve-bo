package be.brahms.TFE_RentServe.repositories;

import be.brahms.TFE_RentServe.models.entities.UserMaterial;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository for managing User Material entity. Provides basic CRUD operations and more Using
 * JpaRepository
 */
@Repository
public interface UserMaterialRepository extends JpaRepository<UserMaterial, Long> {

  /**
   * Find all user material from user ID and is available
   *
   * @param userId the identifier user ID
   * @return a list of user material by user id and available
   */
  @Query("SELECT um FROM UserMaterial um WHERE um.user.id = :userId AND um.isAvailable = true")
  List<UserMaterial> findAllUserMaterialIsActivated(@Param("userId") long userId);

  /**
   * Find all user material from user ID and is not available
   *
   * @param userId the identifier user ID
   * @return a list of user material by user id and is not available
   */
  @Query("SELECT um FROM UserMaterial um WHERE um.user.id = :userId AND um.isAvailable = false")
  List<UserMaterial> findAllUserMaterialIsDeactivated(@Param("userId") long userId);

  /**
   * Find all user materials by user ID
   *
   * @param userId the identifier user
   * @return a list of user materials grouped by id user
   */
  @Query("SELECT um FROM UserMaterial um WHERE um.user.id = :userId")
  List<UserMaterial> findUserMaterialByUserId(@Param("userId") long userId);

  /**
   * Get a boolean true or false if the picture exists or not
   *
   * @param id the identifier of picture
   * @return a boolean true or false
   */
  boolean existsByPictures_Id(long id);

  /**
   * Find all user materials by material ID
   *
   * @param nameMaterial the identifier material
   * @return a list of user materials grouped by id material
   */
  @Query(
      "SELECT um FROM UserMaterial um WHERE um.material.nameMaterial =:nameMaterial AND um.isAvailable = true ")
  List<UserMaterial> findAllUserMaterialsByNameMaterial(@Param("nameMaterial") String nameMaterial);

  /**
   * Get a boolean true or false if the name of material exists or not
   *
   * @param nameMaterial the name of material
   * @return a boolean true or false
   */
  boolean existsByMaterial_NameMaterial(String nameMaterial);
}
