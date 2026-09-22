package be.brahms.TFE_RentServe.services.impl;

import be.brahms.TFE_RentServe.exceptions.material.MaterialNotFoundException;
import be.brahms.TFE_RentServe.exceptions.user.UserNotFoundException;
import be.brahms.TFE_RentServe.exceptions.userMaterial.UserMaterialEmptyException;
import be.brahms.TFE_RentServe.exceptions.userMaterial.UserMaterialException;
import be.brahms.TFE_RentServe.exceptions.userMaterial.UserMaterialNotFoundException;
import be.brahms.TFE_RentServe.mappers.UserMaterialMapper;
import be.brahms.TFE_RentServe.models.dtos.userMaterial.UserMaterialByIdDTO;
import be.brahms.TFE_RentServe.models.dtos.userMaterial.UserMaterialDTO;
import be.brahms.TFE_RentServe.models.entities.Material;
import be.brahms.TFE_RentServe.models.entities.Picture;
import be.brahms.TFE_RentServe.models.entities.User;
import be.brahms.TFE_RentServe.models.entities.UserMaterial;
import be.brahms.TFE_RentServe.models.forms.userMaterial.UserMaterialCreateForm;
import be.brahms.TFE_RentServe.models.forms.userMaterial.UserMaterialUpdateForm;
import be.brahms.TFE_RentServe.repositories.MaterialRepository;
import be.brahms.TFE_RentServe.repositories.PictureRepository;
import be.brahms.TFE_RentServe.repositories.UserMaterialRepository;
import be.brahms.TFE_RentServe.repositories.UserRepository;
import be.brahms.TFE_RentServe.services.UserMaterialService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * Service implementation for managing UserMaterial. Uses UserMaterialRepository to perform database
 * operations uses UserMaterialMapper to map between form to entity or dto to entity
 */
@Service
public class UserMaterialServiceImpl implements UserMaterialService {

  private final UserMaterialRepository userMaterialRepository;
  private final UserMaterialMapper userMaterialMapper;
  private final UserRepository userRepository;
  private final MaterialRepository materialRepository;
  private final PictureRepository pictureRepository;

  /**
   * Constructor with parameters
   *
   * @param userMaterialRepository the userMaterialRepo to access userMaterial data
   * @param userMaterialMapper map between from userMaterial to entity or dto to entity
   * @param userRepository the userRepo to access User data
   * @param materialRepository the materialRepo to access Material data
   * @param pictureRepository the pictureRepo to access Picture data
   */
  @Autowired
  public UserMaterialServiceImpl(
      UserMaterialRepository userMaterialRepository,
      UserMaterialMapper userMaterialMapper,
      UserRepository userRepository,
      MaterialRepository materialRepository,
      PictureRepository pictureRepository) {
    this.userMaterialRepository = userMaterialRepository;
    this.userMaterialMapper = userMaterialMapper;
    this.userRepository = userRepository;
    this.materialRepository = materialRepository;
    this.pictureRepository = pictureRepository;
  }

  /**
   * Get a list of all users material If list is empty, send an exception
   *
   * @return a list of user material
   */
  @Override
  public List<UserMaterialDTO> findAllUserMaterials() {
    List<UserMaterial> listUserMaterials = userMaterialRepository.findAll();

    if (listUserMaterials.isEmpty()) {
      throw new UserMaterialEmptyException();
    }

    return listUserMaterials.stream().map(userMaterialMapper::toListDto).toList();
  }

  /**
   * Get a list of user material from the owner's user and available
   *
   * @param userId the user identifier
   * @return a list of user material from user ID and available true
   */
  @Override
  public List<UserMaterialDTO> findAllUserMaterialIsActivated(long userId) {
    List<UserMaterial> userMaterialListAvailable =
        userMaterialRepository.findAllUserMaterialIsActivated(userId);

    userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

    if (userMaterialListAvailable.isEmpty()) {
      throw new UserMaterialException("The list with user material is empty");
    }
    return userMaterialListAvailable.stream().map(userMaterialMapper::toListDto).toList();
  }

  /**
   * Get a list of user material from the owner's user and is not available
   *
   * @param userId the user identifier
   * @return a list of user material from user ID and available is false
   */
  @Override
  public List<UserMaterialDTO> findAllUserMaterialIsDeactivated(long userId) {
    List<UserMaterial> userMaterialListNotAvailable =
        userMaterialRepository.findAllUserMaterialIsDeactivated(userId);

    userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

    if (userMaterialListNotAvailable.isEmpty()) {
      throw new UserMaterialException("The list with user material is empty");
    }
    return userMaterialListNotAvailable.stream().map(userMaterialMapper::toListDto).toList();
  }

  /**
   * Get a user material by id
   *
   * @param id the identifier of user material
   * @return a detail user material
   */
  @Override
  public UserMaterialByIdDTO findUserMaterialById(long id) {
    UserMaterial userMaterial =
        userMaterialRepository.findById(id).orElseThrow(UserMaterialNotFoundException::new);

    return userMaterialMapper.toIdDto(userMaterial);
  }

  /**
   * Get a list of user material by user ID
   *
   * @param id the identifier of user
   * @return get a list of user material grouped by user ID
   */
  @Override
  public List<UserMaterialDTO> findAllUserMaterialByUserId(long id) {
    List<UserMaterial> listUserMaterialByUser = userMaterialRepository.findUserMaterialByUserId(id);

    if (userRepository.findById(id).isEmpty()) {
      throw new UserNotFoundException();
    }

    if (listUserMaterialByUser.isEmpty()) {
      throw new UserMaterialEmptyException();
    }

    return listUserMaterialByUser.stream().map(userMaterialMapper::toListDto).toList();
  }

  /**
   * Create a new UserMaterial. It's Check if the Material exist. Must be connected to create a new
   * userMaterial
   *
   * @param form the form to create a new user Material
   * @return a new User material
   */
  @Override
  public UserMaterialDTO createUserMaterial(UserMaterialCreateForm form) {

    Material materialById =
        materialRepository.findById(form.materialId()).orElseThrow(MaterialNotFoundException::new);

    UserMaterial userMaterial = userMaterialMapper.fromUserMaterialForm(form);

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication != null && authentication.isAuthenticated()) {
      Object principal = authentication.getPrincipal();

      if (principal instanceof UserDetails userDetails) {
        String pseudo = userDetails.getUsername();

        User user = userRepository.findByPseudo(pseudo).orElseThrow(UserNotFoundException::new);
        userMaterial.setUser(user);
      }

      userMaterial.setMaterial(materialById);
      userMaterial.setDescriptionMaterial(form.descriptionMaterial());
      userMaterial.setPriceHourMaterial(form.priceHourMaterial());
      userMaterial.setStateMaterial(form.state());
      userMaterial.setAvailable(form.isAvailable());

      Set<Picture> picturesSource = userMaterial.getPictures();

      if (picturesSource.isEmpty()) {
        throw new UserMaterialException("Il n'y a aucune photo");
      }

      Set<Picture> pictures =
          picturesSource.stream().map(pictureRepository::save).collect(Collectors.toSet());
      ;

      userMaterial.setPictures(pictures);

      userMaterialRepository.save(userMaterial);
    }
    return userMaterialMapper.toDto(userMaterial);
  }

  /**
   * Update an userMaterial
   *
   * @param id the identifier of user material
   * @param form the form to update the user material
   * @return an updated user material
   */
  @Override
  public UserMaterialDTO updateUserMaterial(long id, UserMaterialUpdateForm form) {

    Material materialById =
        materialRepository.findById(form.materialId()).orElseThrow(MaterialNotFoundException::new);

    UserMaterial userMaterial =
        userMaterialRepository.findById(id).orElseThrow(UserMaterialNotFoundException::new);

    Long existingMaterialId = userMaterial.getMaterial().getId();
    Long newMaterialId = form.materialId();

    if (newMaterialId != null && !newMaterialId.equals(existingMaterialId)) {
      Material newMaterial =
          materialRepository.findById(newMaterialId).orElseThrow(MaterialNotFoundException::new);
      userMaterial.setMaterial(newMaterial);
    }

    userMaterial.setDescriptionMaterial(form.descriptionMaterial());
    userMaterial.setPriceHourMaterial(form.priceHourMaterial());
    userMaterial.setAvailable(form.isAvailable());
    userMaterial.setStateMaterial(form.state());

    userMaterialMapper.fromUpdateUserMaterialForm(form, userMaterial);

    userMaterialRepository.save(userMaterial);

    return userMaterialMapper.toDto(userMaterial);
  }

  /**
   * Delete a user material remove all identifier in relationship with picture_user_material remove
   * all picture from picture database
   *
   * @param id the identifier of user material
   */
  @Override
  public void deleteUserMaterialById(long id) {
    UserMaterial userMaterial =
        userMaterialRepository.findById(id).orElseThrow(UserMaterialNotFoundException::new);

    // Copy the pictures before to break the relationShip UserMaterial to Picture
    Set<Picture> pictures = new HashSet<>(userMaterial.getPictures());

    // Break the relationship and clears all pictures
    userMaterial.getPictures().clear();
    userMaterialRepository.save(userMaterial);

    // Delete user material
    userMaterialRepository.delete(userMaterial);

    // Remove picture like orphan remove
    for (Picture p : pictures) {
      boolean stillUsed = userMaterialRepository.existsByPictures_Id(p.getId());
      if (!stillUsed) {
        pictureRepository.delete(p);
      }
    }
  }

  /**
   * Get a list of all users materials grouped by ID material
   *
   * @param materialId the identifier material
   * @return a list of user material by material ID
   */
  @Override
  public List<UserMaterialDTO> findAllUserMaterialsByMaterialId(long materialId) {
    List<UserMaterial> listUserMaterials =
        userMaterialRepository.findAllUserMaterialsByMaterialId(materialId);

    if (!userMaterialRepository.existsById(materialId)) {
      throw new UserMaterialNotFoundException();
    }

    if (listUserMaterials.isEmpty()) {
      throw new UserMaterialNotFoundException();
    }

    return listUserMaterials.stream().map(userMaterialMapper::toListDto).toList();
  }
}
