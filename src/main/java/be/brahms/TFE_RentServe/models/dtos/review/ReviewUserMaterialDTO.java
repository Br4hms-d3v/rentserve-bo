package be.brahms.TFE_RentServe.models.dtos.review;

import be.brahms.TFE_RentServe.models.dtos.user.UserPseudoDTO;
import be.brahms.TFE_RentServe.models.dtos.userMaterial.UserMaterialNameDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

/**
 * A Dto (Data transfer Object) for review. It contains data about the review like comment and
 * rating
 *
 * @param id the identifier
 * @param comment the comment
 * @param rating the evaluates with a note on the material or favor
 * @param isActive The review is available
 * @param user the pseudo of user
 * @param userMaterialName the name of user material
 * @param createAt the date create
 * @param updateAt the date of updated
 */
public record ReviewUserMaterialDTO(
    Long id,
    String comment,
    Double rating,
    Boolean isActive,
    UserPseudoDTO user,
    UserMaterialNameDTO userMaterialName,
    @JsonFormat(pattern = "dd/MM/yyyy") LocalDate createAt,
    @JsonFormat(pattern = "dd/MM/yyyy") LocalDate updateAt) {}
