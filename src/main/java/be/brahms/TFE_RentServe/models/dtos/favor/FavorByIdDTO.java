package be.brahms.TFE_RentServe.models.dtos.favor;

import be.brahms.TFE_RentServe.models.dtos.category.CategoryDTO;

/**
 * A Dto (Data transfer Object) for favor information.
 * It contains simple data about the favor and his details
 *
 * @param id          The unique identifier
 * @param nameFavor   The name of service
 * @param isAvailable The favor is available
 * @param nameCategory The name of category
 */
public record FavorByIdDTO(
        Long id,
        String nameFavor,
        boolean isAvailable,
        String nameCategory
) {
}
