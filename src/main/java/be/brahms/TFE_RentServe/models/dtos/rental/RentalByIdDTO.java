package be.brahms.TFE_RentServe.models.dtos.rental;

import be.brahms.TFE_RentServe.models.dtos.userFavor.UserFavorNameDTO;
import be.brahms.TFE_RentServe.models.dtos.userMaterial.UserMaterialNameDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Data Transfer Object for a Rental detail. This record used to transfer data between the rental
 * and server
 *
 * @param id the identifier of rental
 * @param amount the amount
 * @param dateStart the date started
 * @param dateEnd the date ended
 * @param startTime the time started
 * @param endTime the time ended
 * @param userFavor the name fo favor
 * @param userMaterial the name of material
 * @param durationDays the time spend on day
 * @param durationHours the time spend on hour
 */
public record RentalByIdDTO(
    Long id,
    BigDecimal amount,
    LocalDate dateStart,
    LocalDate dateEnd,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm") LocalTime startTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm") LocalTime endTime,
    UserFavorNameDTO userFavor,
    UserMaterialNameDTO userMaterial,
    Long durationDays,
    Long durationHours) {}
