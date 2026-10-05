package be.brahms.TFE_RentServe.services;

import be.brahms.TFE_RentServe.models.dtos.userFavor.UserFavorByIdDTO;
import be.brahms.TFE_RentServe.models.dtos.userFavor.UserFavorDTO;
import be.brahms.TFE_RentServe.models.forms.userFavor.UpdateUserFavorForm;
import be.brahms.TFE_RentServe.models.forms.userFavor.UserFavorCreateForm;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service interface for managing user favour. Defines business operations related to userFavor
 * entity
 */
public interface UserFavorService {

  /**
   * This method get a list of all users favours
   *
   * @return list of users favour
   */
  List<UserFavorDTO> findAllUserFavour();

  /**
   * This method get a list of user favour by Favor ID
   *
   * @param nameFavor the name of favor
   * @return a list of users favour grouped by favor id
   */
  List<UserFavorDTO> findAllUserFavourByNameFavor(String nameFavor);

  /**
   * This method get a list of user favor by ID
   *
   * @param id the identifier of user favor
   * @return a detail about the user favor
   */
  UserFavorByIdDTO findUserFavorById(long id);

  /**
   * This method get a detail of user favor by owner user
   *
   * @param id the identifier of user favor
   * @return a detail about the user favor only user can see
   */
  UserFavorByIdDTO findUserFavorByOwnerId(long id);

  /**
   * This method get a list of user favour by User ID
   *
   * @param userId the identifier of user
   * @return a list of users favour grouped by user id
   */
  List<UserFavorDTO> findAllUserFavourByUserId(long userId);

  /**
   * This method get a list of user favour from user id and available
   *
   * @param userId the user identifier
   * @return a list of user favour from user and is available
   */
  List<UserFavorDTO> findAllUserFavourIsActivated(long userId);

  /**
   * This method get a list of user favour from user id and is not available
   *
   * @param userId the user identifier
   * @return a list of user favour from user and is not available
   */
  List<UserFavorDTO> findAllUserFavourIsDeactivated(long userId);

  /**
   * This method saves a new UserFavor
   *
   * @param form the form to create a new user Favor
   * @param pictures the pictures of the user favor
   * @return the saved user favor
   */
  UserFavorDTO createUserFavor(@Valid UserFavorCreateForm form, List<MultipartFile> pictures);

  /**
   * This method update the existing user favor
   *
   * @param id the identifier of user favor
   * @param form the form to update the user favor
   * @return a user favor updated
   */
  UserFavorDTO updateUserFavor(long id, @Valid UpdateUserFavorForm form);

  /**
   * This method delete a userFavor
   *
   * @param id the identifier of user favor
   */
  void deleteUserFavor(long id);
}
