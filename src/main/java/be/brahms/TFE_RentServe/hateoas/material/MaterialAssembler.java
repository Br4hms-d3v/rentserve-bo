package be.brahms.TFE_RentServe.hateoas.material;

import be.brahms.TFE_RentServe.controller.MaterialController;
import be.brahms.TFE_RentServe.models.dtos.material.MaterialDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * This assembler adds useful navigation links related to material
 * operations such as retrieving
 */
@Component
public class MaterialAssembler implements RepresentationModelAssembler<MaterialDTO, EntityModel<MaterialDTO>> {

    /**
     * Constructor by default for MaterialAssembler
     */
    public MaterialAssembler() {

    }

    /**
     * Convert a MaterialDto to an EntityModel with HATEOAS links.
     * This method adds useful links to the MaterialDto,
     * - get a list of all materials
     *
     * @param material the material data to wrap
     * @return an EntityModel with the material data and HATEOAS links
     */
    @Override
    public EntityModel<MaterialDTO> toModel(MaterialDTO material) {
        return EntityModel.of(material,
                linkTo(methodOn(MaterialController.class).findAllMaterials()).withRel("List of materials")
        );
    }

    public CollectionModel<MaterialDTO> toCollectionModel(List<MaterialDTO> materials) {
        return CollectionModel.of(materials,
                linkTo(methodOn(MaterialController.class).findAllMaterials()).withRel("List of materials")
        );
    }
}
