package be.brahms.TFE_RentServe.models.forms.rental;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Record rentalUpdateForm into a rental Entity
 *
 * @param dateStart the date start
 * @param dateEnd the date end
 * @param startTime the time start
 * @param endTime the time ended
 */
public record RentalUpdateForm(
    @NotNull LocalDate dateStart,
    @NotNull LocalDate dateEnd,
    @NotNull @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm") LocalTime startTime,
    @NotNull @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm") LocalTime endTime) {}
