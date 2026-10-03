package be.brahms.TFE_RentServe.repositories;

import be.brahms.TFE_RentServe.models.entities.UserFavor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository for managing User Favor entity. Provides basic CRUD operations and more Using
 * JpaRepository
 */
@Repository
public interface UserFavorRepository extends JpaRepository<UserFavor, Long> {

  /**
   * Find all users favour by favor ID
   *
   * @param favorId the identifier favor
   * @return a list of user favor grouped by id favor
   */
  @Query("SELECT uf FROM UserFavor uf WHERE uf.favor.id = :favorId AND uf.isAvailable")
  List<UserFavor> findAllUserFavourByFavorId(@Param("favorId") long favorId);

  /**
   * Find all users favour by user ID
   *
   * @param userId the identifier user
   * @return a list of user favour grouped by id user
   */
  @Query("SELECT uf FROM UserFavor uf WHERE uf.user.id = :userId")
  List<UserFavor> findAllUserFavourByUserId(@Param("userId") long userId);

  /**
   * Find all user favour from user ID and is available
   *
   * @param userId the identifier user ID
   * @return a list of user favour by user id and available
   */
  @Query("SELECT uf FROM UserFavor uf WHERE uf.user.id = :userId AND uf.isAvailable = true")
  List<UserFavor> findAllUserFavourIsActivated(@Param("userId") long userId);

  /**
   * Find all user favour from user ID and is not available
   *
   * @param userId the identifier user ID
   * @return a list of user favour by user id and is not available
   */
  @Query("SELECT uf FROM UserFavor uf WHERE uf.user.id = :userId AND uf.isAvailable = false")
  List<UserFavor> findAllUserFavourIsDeactivated(@Param("userId") long userId);

  /**
   * Get a boolean true or false if the picture exists or not
   *
   * @param id the identifier of picture
   * @return a boolean true or false
   */
  boolean existsByPictures_Id(long id);
}
