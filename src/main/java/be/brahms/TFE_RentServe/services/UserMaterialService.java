package be.brahms.TFE_RentServe.services;

import be.brahms.TFE_RentServe.models.dtos.userMaterial.UserMaterialByIdDTO;
import be.brahms.TFE_RentServe.models.dtos.userMaterial.UserMaterialDTO;
import be.brahms.TFE_RentServe.models.forms.userMaterial.UserMaterialCreateForm;
import be.brahms.TFE_RentServe.models.forms.userMaterial.UserMaterialUpdateForm;
import jakarta.validation.Valid;
import java.util.List;

/**
 * Service interface for managing user materials. Defines business operations related to
 * userMaterial entity
 */
public interface UserMaterialService {

  /**
   * This method get a list of all users materials
   *
   * @return list of users materials
   */
  List<UserMaterialDTO> findAllUserMaterials();

  /**
   * This method get a list of user material from user id and available
   *
   * @param userId the user identifier
   * @return a list of user material from user and is available
   */
  List<UserMaterialDTO> findAllUserMaterialIsActivated(long userId);

  /**
   * This method get a list of user material from user id and is not available
   *
   * @param userId the user identifier
   * @return a list of user material from user and is not available
   */
  List<UserMaterialDTO> findAllUserMaterialIsDeactivated(long userId);

  /**
   * This method get a list of user material by ID
   *
   * @param id the identifier of user material
   * @return a detail about the user material
   */
  UserMaterialByIdDTO findUserMaterialById(long id);

  /**
   * This method get a detail of user material by owner user
   *
   * @param id the identifier of user material
   * @return a detail about the user material only user can see
   */
  UserMaterialByIdDTO findUserMaterialByOwnerId(long id);

  /**
   * This method get a list of user material by User ID
   *
   * @param userId the identifier of user
   * @return a list of users material grouped by user id
   */
  List<UserMaterialDTO> findAllUserMaterialByUserId(long userId);

  /**
   * This method saves a new UserMaterial
   *
   * @param form the form to create a new user Material
   * @return the saved user material
   */
  UserMaterialDTO createUserMaterial(@Valid UserMaterialCreateForm form);

  /**
   * This method update the existing user material
   *
   * @param id the identifier of user material
   * @param form the form to update the user material
   * @return an updated user material
   */
  UserMaterialDTO updateUserMaterial(long id, @Valid UserMaterialUpdateForm form);

  /**
   * This method delete a userMaterial
   *
   * @param id the identifier of user material
   */
  void deleteUserMaterialById(long id);

  /**
   * This method get a list of user materials by Material ID
   *
   * @param materialId the identifier material
   * @return a list of users materials grouped by material id
   */
  List<UserMaterialDTO> findAllUserMaterialsByMaterialId(long materialId);
}
