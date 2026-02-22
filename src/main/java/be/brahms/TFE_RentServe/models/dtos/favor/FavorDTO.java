package be.brahms.TFE_RentServe.models.dtos.favor;

/**
 * FavorDTO is a data transfer object for favor information
 * It holds favor details like name of favor
 *
 * @param id        the identifier of the favor
 * @param nameFavor the name of favor
 */
public record FavorDTO(
        Long id,
        String nameFavor
) {
}
