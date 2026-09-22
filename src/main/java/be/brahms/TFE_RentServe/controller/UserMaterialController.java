package be.brahms.TFE_RentServe.controller;

import be.brahms.TFE_RentServe.hateoas.userMaterial.UserMaterialAssembler;
import be.brahms.TFE_RentServe.models.dtos.userMaterial.UserMaterialByIdDTO;
import be.brahms.TFE_RentServe.models.dtos.userMaterial.UserMaterialDTO;
import be.brahms.TFE_RentServe.models.forms.userMaterial.UserMaterialCreateForm;
import be.brahms.TFE_RentServe.models.forms.userMaterial.UserMaterialUpdateForm;
import be.brahms.TFE_RentServe.services.UserMaterialService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * This controller manages user material
 *
 * <ul>
 *   <li>Get a list of user materials
 *   <li>Get a list of user materials by Material ID
 *   <li>Get a list of user materials activated
 *   <li>Get a list of user materials deactivated
 *   <li>Get details of the user materials by his ID
 *   <li>Get a list of user material by grouped user ID
 *   <li>Update a user material
 *   <li>Delete a user material by ID
 *   <li>Create a new user material
 * </ul>
 *
 * @author Brahim K
 */
@RestController
@RequestMapping("/api/user-material/")
public class UserMaterialController {

  private final UserMaterialService userMaterialService;
  private final UserMaterialAssembler userMaterialAssembler;

  /**
   * This constructor is used to inject the necessary service for handling user material related
   * request
   *
   * @param userMaterialService the service used for user material management
   * @param userMaterialAssembler the assembler used to convert User material object to into
   *     UserMaterialDto
   */
  public UserMaterialController(
      UserMaterialService userMaterialService, UserMaterialAssembler userMaterialAssembler) {
    this.userMaterialService = userMaterialService;
    this.userMaterialAssembler = userMaterialAssembler;
  }

  /**
   * Get a list of all users materials
   *
   * @return a list of users material
   */
  @GetMapping("list")
  @PreAuthorize("hasAnyRole('MEMBER', 'MODERATOR', 'ADMIN')")
  public ResponseEntity<CollectionModel<UserMaterialDTO>> getAllUserMaterials() {
    List<UserMaterialDTO> userMaterials = userMaterialService.findAllUserMaterials();
    CollectionModel<UserMaterialDTO> userMaterialCollectionModel =
        userMaterialAssembler.toCollectionModel(userMaterials);
    return ResponseEntity.ok().body(userMaterialCollectionModel);
  }

  /**
   * Get a list of user material activated from the user ID owner
   *
   * @param id the identifier user
   * @return a list of user material activated
   */
  @GetMapping("user/{id}/activated")
  @PreAuthorize("hasAnyRole('MEMBER', 'MODERATOR')")
  public ResponseEntity<CollectionModel<UserMaterialDTO>> getActiveUserMaterials(
      @PathVariable long id) {
    List<UserMaterialDTO> userMaterialActivated =
        userMaterialService.findAllUserMaterialIsActivated(id);
    CollectionModel<UserMaterialDTO> userMaterialsActivated =
        userMaterialAssembler.toCollectionModel(userMaterialActivated);

    return ResponseEntity.ok().body(userMaterialsActivated);
  }

  /**
   * Get a list of user material deactivated from the user ID owner
   *
   * @param id the identifier user
   * @return a list of user material deactivated
   */
  @GetMapping("user/{id}/deactivated")
  @PreAuthorize("hasAnyRole('MEMBER', 'MODERATOR')")
  public ResponseEntity<CollectionModel<UserMaterialDTO>> getDeactivateUserMaterials(
      @PathVariable long id) {
    List<UserMaterialDTO> userMaterialDeactivated =
        userMaterialService.findAllUserMaterialIsDeactivated(id);
    CollectionModel<UserMaterialDTO> userMaterialsDeactivated =
        userMaterialAssembler.toCollectionModel(userMaterialDeactivated);

    return ResponseEntity.ok().body(userMaterialsDeactivated);
  }

  /**
   * Get a detail about the user material by ID
   *
   * @param id the identifier of User material
   * @return a detail about the user material
   */
  @GetMapping("{id}")
  @PreAuthorize("hasAnyRole('MEMBER', 'MODERATOR', 'ADMIN')")
  public ResponseEntity<EntityModel<UserMaterialByIdDTO>> getUserMaterialById(
      @PathVariable long id) {
    UserMaterialByIdDTO userMaterialByIdDTO = userMaterialService.findUserMaterialById(id);
    return ResponseEntity.ok().body(userMaterialAssembler.toIdModel(userMaterialByIdDTO));
  }

  /**
   * Get a detail about the user material by owner user
   *
   * @param id the identifier of User material
   * @return a detail about the user material
   */
  @GetMapping("my-material/{id}")
  @PreAuthorize("hasAnyRole('MEMBER', 'MODERATOR')")
  public ResponseEntity<EntityModel<UserMaterialByIdDTO>> getUserMaterialDetailByOwnerId(
          @PathVariable long id) {
    UserMaterialByIdDTO userMaterialByIdDTO = userMaterialService.findUserMaterialByOwnerId(id);
    return ResponseEntity.ok().body(userMaterialAssembler.toIdModel(userMaterialByIdDTO));
  }

  /**
   * Get a list of user material grouped by User
   *
   * @param id the identifier from User
   * @return a list of user material by user id
   */
  @GetMapping("user/{id}")
  @PreAuthorize("hasAnyRole('MEMBER', 'MODERATOR', 'ADMIN')")
  public ResponseEntity<CollectionModel<UserMaterialDTO>> getUserMaterialByUserId(
      @PathVariable long id) {
    List<UserMaterialDTO> userMaterialUserId = userMaterialService.findAllUserMaterialByUserId(id);
    CollectionModel<UserMaterialDTO> userMaterialDTOCollectionModel =
        userMaterialAssembler.toCollectionModel(userMaterialUserId);
    return ResponseEntity.ok().body(userMaterialDTOCollectionModel);
  }

  /**
   * Create a new UserMaterial
   *
   * @param form the form to create a new User Material
   * @return a new User material
   */
  @PostMapping("new")
  @PreAuthorize("hasAnyRole('MEMBER', 'MODERATOR', 'ADMIN')")
  public ResponseEntity<EntityModel<UserMaterialDTO>> createUserMaterial(
      @RequestBody @Valid UserMaterialCreateForm form) {
    UserMaterialDTO newUserMaterial = userMaterialService.createUserMaterial(form);
    return ResponseEntity.ok().body(userMaterialAssembler.toModel(newUserMaterial));
  }

  /**
   * Update the useMaterial
   *
   * @param id the identifier of user material
   * @param form the form to update the user material
   * @return a user material updated
   */
  @PutMapping("{id}/edit")
  @PreAuthorize("hasAnyRole('MEMBER', 'MODERATOR', 'ADMIN')")
  public ResponseEntity<EntityModel<UserMaterialDTO>> updateUserMaterial(
      @PathVariable long id, @RequestBody UserMaterialUpdateForm form) {
    UserMaterialDTO newUserMaterial = userMaterialService.updateUserMaterial(id, form);
    return ResponseEntity.ok().body(userMaterialAssembler.toModel(newUserMaterial));
  }

  /**
   * Delete the userMaterial
   *
   * @param id the identifier of user material
   * @return a message to confirm delete
   */
  @DeleteMapping("{id}/delete")
  @PreAuthorize("hasAnyRole('MEMBER', 'MODERATOR', 'ADMIN')")
  public ResponseEntity<String> deleteUserMaterial(@PathVariable long id) {
    userMaterialService.deleteUserMaterialById(id);
    return ResponseEntity.ok().body("The user material has been deleted");
  }

  /**
   * Get a list of all users materials grouped by ID material
   *
   * @param materialId the identifier material
   * @return a list of user materials by Material ID
   */
  @GetMapping("list/{materialId}")
  @PreAuthorize("hasAnyRole('MEMBER', 'MODERATOR', 'ADMIN')")
  public ResponseEntity<CollectionModel<UserMaterialDTO>> findAllUserMaterialsByMaterialId(
      @PathVariable long materialId) {
    List<UserMaterialDTO> userMaterialDTOs =
        userMaterialService.findAllUserMaterialsByMaterialId(materialId);
    CollectionModel<UserMaterialDTO> userMaterialDTOCollectionModel =
        userMaterialAssembler.toCollectionModel(userMaterialDTOs);
    return ResponseEntity.ok().body(userMaterialDTOCollectionModel);
  }
}
