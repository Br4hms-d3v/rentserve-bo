package be.brahms.TFE_RentServe.services;

import be.brahms.TFE_RentServe.models.dtos.material.MaterialDTO;

import java.util.List;

/**
 * Service interface for managing material.
 * Defines business operations related to entity
 */
public interface MaterialService {

    /**
     * This method get a list of materials
     *
     * @return a list of materials
     */
    List<MaterialDTO> findAllMaterials();
}
