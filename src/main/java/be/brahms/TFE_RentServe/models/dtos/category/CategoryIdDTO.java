package be.brahms.TFE_RentServe.models.dtos.category;

import java.util.Date;

/**
 * CategoryIdDTO is a data transfer object for category with more details information
 *
 * @param id           the identifier of category
 * @param nameCategory the name of category
 * @param createdAt    the date when the category was created
 * @param updatedAt    the date when the category was modified
 */
public record CategoryIdDTO(
        Long id,
        String nameCategory,
        Date createdAt,
        Date updatedAt
) {
}
