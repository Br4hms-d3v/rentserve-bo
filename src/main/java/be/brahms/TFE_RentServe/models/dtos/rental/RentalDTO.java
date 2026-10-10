package be.brahms.TFE_RentServe.models.dtos.rental;

import be.brahms.TFE_RentServe.models.dtos.userFavor.UserFavorNameDTO;
import be.brahms.TFE_RentServe.models.dtos.userMaterial.UserMaterialNameDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record RentalDTO(
        Long id,
        BigDecimal amount,
        LocalDate dateStart,
        LocalDate dateEnd,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm") LocalTime startTime,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm") LocalTime endTime,
        UserFavorNameDTO userFavor,
        UserMaterialNameDTO userMaterial
) {
}
