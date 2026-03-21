package be.brahms.TFE_RentServe.models.dtos.material;

/**
 * MaterialDTO is a data transfer object for material information
 * It holds favor details like name of material
 *
 * @param id the identifier of the material
 * @param nameMaterial the name of material
 */
public record MaterialDTO(
        Long id,
        String nameMaterial
) {
}
