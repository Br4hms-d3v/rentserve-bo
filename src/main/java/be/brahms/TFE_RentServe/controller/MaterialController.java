package be.brahms.TFE_RentServe.controller;

import be.brahms.TFE_RentServe.hateoas.material.MaterialAssembler;
import be.brahms.TFE_RentServe.models.dtos.material.MaterialDTO;
import be.brahms.TFE_RentServe.services.MaterialService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * This controller manages Material
 * It has a method to display a list of materials
 *
 * @author Brahim K
 */
@RestController
@RequestMapping("/api/material")
public class MaterialController {

    private final MaterialService materialService;
    private final MaterialAssembler materialAssembler;

    public MaterialController(MaterialService materialService, MaterialAssembler materialAssembler) {
        this.materialService = materialService;
        this.materialAssembler = materialAssembler;
    }

    /**
     * Get a list of materials
     *
     * @return a list of materials
     */
    @GetMapping("list")
    public ResponseEntity<CollectionModel<MaterialDTO>> findAllMaterials() {
        List<MaterialDTO> materials = materialService.findAllMaterials();
        CollectionModel<MaterialDTO> modelMaterials = materialAssembler.toCollectionModel(materials);
        return ResponseEntity.ok(modelMaterials);
    }
}
