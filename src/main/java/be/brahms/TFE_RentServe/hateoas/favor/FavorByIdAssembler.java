package be.brahms.TFE_RentServe.hateoas.favor;

import be.brahms.TFE_RentServe.controller.FavorController;
import be.brahms.TFE_RentServe.models.dtos.favor.FavorByIdDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * FavorByIdDTO is a class that helps to convert CategoryDto objects
 * into EntityModel objects. It creates models with links for category data.
 */
@Component
public class FavorByIdAssembler implements RepresentationModelAssembler<FavorByIdDTO, EntityModel<FavorByIdDTO>> {

    /**
     * Constructor by default for FavorIdAssembler
     */
    public FavorByIdAssembler() {
    }

    /**
     * Convert a FavorByIdDto to an EntityModel with HATEOAS links.
     * This method adds useful links to the CategoryDto,
     * - a link for category:
     * - get details category
     * - create a new category
     * - edit a category
     * - search category by name
     * - delete a category
     *
     * @param favor the category data to wrap
     * @return an EntityModel with the category data and HATEOAS links
     */
    @Override
    public EntityModel<FavorByIdDTO> toModel(FavorByIdDTO favor) {
        return EntityModel.of(favor,
                linkTo(methodOn(FavorController.class).getFavorById(favor.id())).withRel("Get detail by id: " + favor.id()),
                linkTo(methodOn(FavorController.class).getAllFavourByNameCategory(null)).withRel("List of favour by Category"),
                linkTo(methodOn(FavorController.class).getAllFavors()).withRel("List of all favour"),
                linkTo(methodOn(FavorController.class).createFavor(null)).withRel("Create a new favor"),
                linkTo(methodOn(FavorController.class).updateFavor(favor.id(), null)).withRel("Edit a favor"),
                linkTo(methodOn(FavorController.class).deleteFavor(favor.id())).withRel("Delete a favor")
        );
    }

}
