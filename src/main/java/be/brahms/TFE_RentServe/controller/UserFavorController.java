package be.brahms.TFE_RentServe.controller;

import be.brahms.TFE_RentServe.hateoas.userFavor.UserFavorAssembler;
import be.brahms.TFE_RentServe.models.dtos.userFavor.UserFavorByIdDTO;
import be.brahms.TFE_RentServe.models.dtos.userFavor.UserFavorDTO;
import be.brahms.TFE_RentServe.models.forms.userFavor.UpdateUserFavorForm;
import be.brahms.TFE_RentServe.models.forms.userFavor.UserFavorCreateForm;
import be.brahms.TFE_RentServe.services.UserFavorService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * This controller manages user favor
 *
 * <ul>
 *   <li>Get a list of user favour
 *   <li>Get a list of user favour by favor ID
 *   <li>Get a list of user favour activated
 *   <li>Get a list of user favour deactivated
 *   <li>Get details of the user favor by his ID
 *   <li>Get a list of user favor by grouped user ID
 *   <li>Update a user favor
 *   <li>Delete a user favor by ID
 *   <li>Create a new user favor
 * </ul>
 *
 * @author Brahim K
 */
@RestController
@RequestMapping("/api/user-favor/")
public class UserFavorController {

  private final UserFavorService userFavorService;
  private final UserFavorAssembler userFavorAssembler;

  /**
   * This constructor is used to inject the necessary service for handling user favor related
   * request
   *
   * @param userFavorService the service used for user favor management
   * @param userFavorAssembler the assembler used to convert User favor object to into UserFavorDto
   */
  public UserFavorController(
      UserFavorService userFavorService, UserFavorAssembler userFavorAssembler) {
    this.userFavorService = userFavorService;
    this.userFavorAssembler = userFavorAssembler;
  }

  /**
   * Get a list of all users favour
   *
   * @return a list of users favour
   */
  @GetMapping("list")
  @PreAuthorize("hasAnyRole('MEMBER', 'MODERATOR', 'ADMIN')")
  public ResponseEntity<CollectionModel<UserFavorDTO>> getAllUserFavour() {
    List<UserFavorDTO> userFavour = userFavorService.findAllUserFavour();
    CollectionModel<UserFavorDTO> userFavorDTOCollectionModel =
        userFavorAssembler.toCollectionModel(userFavour);
    return ResponseEntity.ok().body(userFavorDTOCollectionModel);
  }

  /**
   * Get a list of all users favour
   *
   * @param favorId the identifier favor
   * @return a list of users favour grouped by favor ID
   */
  @GetMapping("list/{favorId}")
  @PreAuthorize("hasAnyRole('MEMBER', 'MODERATOR', 'ADMIN')")
  public ResponseEntity<CollectionModel<UserFavorDTO>> getUserFavourByFavorId(
      @PathVariable long favorId) {
    List<UserFavorDTO> userFavor = userFavorService.findAllUserFavourByFavorId(favorId);
    CollectionModel<UserFavorDTO> userFavorDTOCollectionModel =
        userFavorAssembler.toCollectionModel(userFavor);
    return ResponseEntity.ok().body(userFavorDTOCollectionModel);
  }

  /**
   * Get a detail about the user favor by ID
   *
   * @param id the identifier of User favor
   * @return a detail about the user favor
   */
  @GetMapping("{id}")
  @PreAuthorize("hasAnyRole('MEMBER', 'MODERATOR', 'ADMIN')")
  public ResponseEntity<EntityModel<UserFavorByIdDTO>> getUserFavorById(@PathVariable long id) {
    UserFavorByIdDTO userFavorId = userFavorService.findUserFavorById(id);
    return ResponseEntity.ok().body(userFavorAssembler.toIdModel(userFavorId));
  }

  /**
   * Get a list of user favor grouped by User
   *
   * @param id the identifier from User
   * @return a list of user favor by user id
   */
  @GetMapping("user/{id}")
  @PreAuthorize("hasAnyRole('MEMBER', 'MODERATOR', 'ADMIN')")
  public ResponseEntity<CollectionModel<UserFavorDTO>> getUserFavorByUserId(@PathVariable long id) {
    List<UserFavorDTO> userFavorByUserId = userFavorService.findAllUserFavourByUserId(id);
    CollectionModel<UserFavorDTO> userFavorDTOCollectionModel =
        userFavorAssembler.toCollectionModel(userFavorByUserId);
    return ResponseEntity.ok().body(userFavorDTOCollectionModel);
  }

  /**
   * Get a list of user favor grouped by User
   *
   * @param id the identifier from User
   * @return a list of user favor by user id
   */
  @GetMapping("my-favor/{id}")
  @PreAuthorize("hasAnyRole('MEMBER', 'MODERATOR', 'ADMIN')")
  public ResponseEntity<EntityModel<UserFavorByIdDTO>> getUserFavorDetailByOwnerId(
      @PathVariable long id) {
    UserFavorByIdDTO userFavorByUserId = userFavorService.findUserFavorByOwnerId(id);
    return ResponseEntity.ok().body(userFavorAssembler.toIdModel(userFavorByUserId));
  }

  /**
   * Get a list of user favor activated from the user ID owner
   *
   * @param id the identifier user
   * @return a list of user favor activated
   */
  @GetMapping("user/{id}/activated")
  @PreAuthorize("hasAnyRole('MEMBER', 'MODERATOR')")
  public ResponseEntity<CollectionModel<UserFavorDTO>> getActivatedUserFavour(
      @PathVariable long id) {
    List<UserFavorDTO> userFavorIsActivated = userFavorService.findAllUserFavourIsActivated(id);
    CollectionModel<UserFavorDTO> userFavorDTOCollectionModel =
        userFavorAssembler.toCollectionModel(userFavorIsActivated);
    return ResponseEntity.ok().body(userFavorDTOCollectionModel);
  }

  /**
   * Get a list of user favor deactivated from the user ID owner
   *
   * @param id the identifier user
   * @return a list of user favor deactivated
   */
  @GetMapping("user/{id}/deactivated")
  @PreAuthorize("hasAnyRole('MEMBER', 'MODERATOR')")
  public ResponseEntity<CollectionModel<UserFavorDTO>> getDeactivatedUserFavour(
      @PathVariable long id) {
    List<UserFavorDTO> userFavorIsDeactivated = userFavorService.findAllUserFavourIsDeactivated(id);
    CollectionModel<UserFavorDTO> userFavorDTOCollectionModel =
        userFavorAssembler.toCollectionModel(userFavorIsDeactivated);
    return ResponseEntity.ok().body(userFavorDTOCollectionModel);
  }

  /**
   * Create a new UseFavor
   *
   * @param form the form to create a new User Favor
   * @param pictures the pictures of the user favor
   * @return a new User favor
   */
  @PostMapping(value = "new", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @PreAuthorize("hasAnyRole('MEMBER', 'MODERATOR', 'ADMIN')")
  public ResponseEntity<EntityModel<UserFavorDTO>> createUserFavor(
      @Valid @ModelAttribute UserFavorCreateForm form,
      @RequestPart("pictures") List<MultipartFile> pictures) {
    UserFavorDTO newUserFavor = userFavorService.createUserFavor(form, pictures);
    return ResponseEntity.ok().body(userFavorAssembler.toModel(newUserFavor));
  }

  /**
   * Update the useFavor
   *
   * @param id the identifier of user favor
   * @param form the form to update the user favor
   * @return a user favor updated
   */
  @PutMapping("{id}/edit")
  @PreAuthorize("hasAnyRole('MEMBER', 'MODERATOR', 'ADMIN')")
  public ResponseEntity<EntityModel<UserFavorDTO>> updateUserFavor(
      @PathVariable long id, @RequestBody UpdateUserFavorForm form) {
    UserFavorDTO userFavor = userFavorService.updateUserFavor(id, form);
    return ResponseEntity.ok().body(userFavorAssembler.toModel(userFavor));
  }

  /**
   * Delete the userFavor
   *
   * @param id the identifier of user favor
   * @return a message to confirm delete
   */
  @DeleteMapping("{id}/delete")
  @PreAuthorize("hasAnyRole('MEMBER', 'MODERATOR', 'ADMIN')")
  public ResponseEntity<String> deleteUserFavorById(@PathVariable long id) {
    userFavorService.deleteUserFavor(id);
    return ResponseEntity.ok().body("The userFavor has been deleted with success");
  }
}
