package be.brahms.TFE_RentServe.hateoas.favor;

import be.brahms.TFE_RentServe.controller.FavorController;
import be.brahms.TFE_RentServe.models.dtos.favor.FavorDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * This assembler adds useful navigation links related to favor
 * operations such as retrieving
 *
 */
@Component
public class FavorAssembler implements RepresentationModelAssembler<FavorDTO, EntityModel<FavorDTO>> {

    /**
     * Constructor by default for FavorAssembler
     */
    public FavorAssembler() {

    }

    /**
     * Convert a FavorDto to an EntityModel with HATEOAS links.
     * This method adds useful links to the FavorDto,
     * - a link for Favor
     * - get a favor by ID
     * - get a list of all favour from a name fo category
     * - get a list of favour
     * - create a favor
     * - edit a favor
     * - delete a favor
     *
     * @param favor the favor data to wrap
     * @return en EntityModel with the favor data and HATEOAS links
     */
    @Override
    public EntityModel<FavorDTO> toModel(FavorDTO favor) {
        return EntityModel.of(favor,
                linkTo(methodOn(FavorController.class).getFavorById(favor.id())).withRel("Get detail by id: " + favor.id()),
                linkTo(methodOn(FavorController.class).getAllFavourByNameCategory(null)).withRel("List of favour by Category"),
                linkTo(methodOn(FavorController.class).getAllFavors()).withRel("List of all favour"),
                linkTo(methodOn(FavorController.class).createFavor(null)).withRel("Create a new favor"),
                linkTo(methodOn(FavorController.class).updateFavor(favor.id(), null)).withRel("Edit a favor"),
                linkTo(methodOn(FavorController.class).deleteFavor(favor.id())).withRel("Delete a favor")
        );
    }

    /**
     * Convert a FavorDto to EntityModel with HATEOAS links.
     * This method adds useful links to the Favor
     *
     * @param favorDTO the favor collection and HATEOAS links
     * @return a list of model with HATEOAS links
     */
    public CollectionModel<FavorDTO> toCollectionModel(List<FavorDTO> favorDTO) {
        return CollectionModel.of(favorDTO,
                linkTo(methodOn(FavorController.class).getAllFavourByNameCategory(null)).withRel("List of favour by Category"),
                linkTo(methodOn(FavorController.class).getAllFavors()).withRel("List of all favour")
        );
    }
}
