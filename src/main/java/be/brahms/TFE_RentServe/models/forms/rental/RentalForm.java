package be.brahms.TFE_RentServe.models.forms.rental;

import be.brahms.TFE_RentServe.models.dtos.userFavor.UserFavorDTO;
import be.brahms.TFE_RentServe.models.dtos.userMaterial.UserMaterialDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Record rental rentalForm into a rental Entity
 *
 * @param dateStart the start date
 * @param dateEnd the end date
 * @param startTime the time start
 * @param endTime the time stop
 * @param userMaterial the name of material
 * @param userFavor the name of favor
 */
public record RentalForm(
    @NotNull LocalDate dateStart,
    @NotNull LocalDate dateEnd,
    @NotNull @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm") LocalTime startTime,
    @NotNull @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm") LocalTime endTime,
    UserMaterialDTO userMaterial,
    UserFavorDTO userFavor) {}
