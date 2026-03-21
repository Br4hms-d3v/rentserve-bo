package be.brahms.TFE_RentServe.services.impl;

import be.brahms.TFE_RentServe.exceptions.material.MaterialException;
import be.brahms.TFE_RentServe.mappers.MaterialMapper;
import be.brahms.TFE_RentServe.models.dtos.material.MaterialDTO;
import be.brahms.TFE_RentServe.models.entities.Material;
import be.brahms.TFE_RentServe.repositories.MaterialRepository;
import be.brahms.TFE_RentServe.services.MaterialService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service implementation for managing Material.
 * Use MaterialRepository to perform database operations.
 *
 * @author Brahim K
 */
@Transactional()
@Service
public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepository materialRepository;
    private final MaterialMapper materialMapper;

    /**
     * Constructor with parameters
     *
     * @param materialRepository the materialRepository to access material
     */
    public MaterialServiceImpl(MaterialRepository materialRepository, MaterialMapper materialMapper) {
        this.materialRepository = materialRepository;
        this.materialMapper = materialMapper;
    }

    public List<MaterialDTO> findAllMaterials() {
        List<Material> materials = materialRepository.findAll();

        if (materials.isEmpty()) {
            throw new MaterialException("The list is empty");
        }

        return materialMapper.toListDto(materials);
    }
}
