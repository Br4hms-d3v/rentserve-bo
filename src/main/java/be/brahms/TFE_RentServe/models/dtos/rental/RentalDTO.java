package be.brahms.TFE_RentServe.models.dtos.rental;

import be.brahms.TFE_RentServe.models.dtos.userFavor.UserFavorNameDTO;
import be.brahms.TFE_RentServe.models.dtos.userMaterial.UserMaterialNameDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Data Transfer Object for a Rental This record is used to transfer data between the rental and
 * server.
 *
 * @param id the identifier of rental
 * @param amount the amount of rental
 * @param dateStart the date start
 * @param dateEnd the date end
 * @param startTime the time start
 * @param endTime the time stop
 * @param userFavor the user favor name
 * @param userMaterial the user material name
 */
public record RentalDTO(
    Long id,
    BigDecimal amount,
    LocalDate dateStart,
    LocalDate dateEnd,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm") LocalTime startTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm") LocalTime endTime,
    UserFavorNameDTO userFavor,
    UserMaterialNameDTO userMaterial) {}
