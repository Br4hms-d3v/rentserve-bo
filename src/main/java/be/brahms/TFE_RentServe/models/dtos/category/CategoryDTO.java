package be.brahms.TFE_RentServe.models.dtos.category;

/**
 * CategoryDTO is a data transfer Object for category information.
 * Get the name of category
 *
 * @param id           the identification of the category
 * @param nameCategory the name of category
 */
public record CategoryDTO(
        Long id,
        String nameCategory
) {
}
