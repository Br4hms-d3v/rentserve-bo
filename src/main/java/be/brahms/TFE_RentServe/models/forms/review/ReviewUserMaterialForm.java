package be.brahms.TFE_RentServe.models.forms.review;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

/**
 *
// * @param id the identifier review
 * @param comment the comment
 * @param userMaterialID the identifier of user material
 * @param rating the evaluates with a note on the material or favor
 * @param isActive The review is available
 */
public record ReviewUserMaterialForm(
//        Long id,
        Long userMaterialID,
        String comment,
        @NotNull
        @DecimalMin(value = "0.0")
        @DecimalMax(value = "5.0")
        Double rating,
        Boolean isActive
) {
}
