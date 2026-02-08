package be.brahms.TFE_RentServe.hateoas.category;

import be.brahms.TFE_RentServe.controller.CategoryController;
import be.brahms.TFE_RentServe.models.dtos.category.CategoryDTO;
import be.brahms.TFE_RentServe.models.dtos.category.CategoryIdDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * CategoryAssembler is a class that helps to convert CategoryDto objects
 * into EntityModel objects. It creates models with links for category data.
 * <p>
 */
@Component
public class CategoryAssembler implements RepresentationModelAssembler<CategoryDTO, EntityModel<CategoryDTO>> {

    /**
     * Convert a CategoryDto to an EntityModel with HATEOAS links.
     * <p>
     * This method adds useful links to the CategoryDto,
     * - a link for category:
     * - get category
     * - get details category
     * - create a new category
     * - edit a category
     * - search category by name
     * - delete a category
     *
     * @param category the category data to wrap
     * @return an EntityModel with the category data and HATEOAS links
     */
    @Override
    public EntityModel<CategoryDTO> toModel(CategoryDTO category) {
        return EntityModel.of(category,
                linkTo(methodOn(CategoryController.class).getAllCategories()).withRel("List of all categories"),
                linkTo(methodOn(CategoryController.class).getCategory(category.id())).withRel("Get Category by ID"),
                linkTo(methodOn(CategoryController.class).createCategory(null)).withRel("Create a new category"),
                linkTo(methodOn(CategoryController.class).updateCategory(category.id(), null)).withRel("Edit category"),
                linkTo(methodOn(CategoryController.class).searchCategory(null)).withRel("Search category by name"),
                linkTo(methodOn(CategoryController.class).deleteCategory(category.id())).withRel("Delete a category")
        );
    }

    /**
     * Convert a CategoryIdDto to an EntityModel with HATEOAS links.
     * <p>
     * This method adds useful links to the CategoryIdDto,
     * a link for category by id
     *
     * @param category the category data to wrap
     * @return an EntityModel with the category details and HATEOAS links
     */
    public EntityModel<CategoryIdDTO> toIdModel(CategoryIdDTO category) {
        return EntityModel.of(category,
                linkTo(methodOn(CategoryController.class).getCategory(category.id())).withRel("Get Category by ID")
        );
    }
}
