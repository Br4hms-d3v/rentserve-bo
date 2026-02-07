package be.brahms.TFE_RentServe.hateoas.category;

import be.brahms.TFE_RentServe.controller.CategoryController;
import be.brahms.TFE_RentServe.models.dtos.category.CategoryDTO;
import be.brahms.TFE_RentServe.models.dtos.category.CategoryIdDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CategoryAssembler implements RepresentationModelAssembler<CategoryDTO, EntityModel<CategoryDTO>> {

    @Override
    public EntityModel<CategoryDTO> toModel(CategoryDTO category) {
        return EntityModel.of(category,
                linkTo(methodOn(CategoryController.class).getAllCategories()).withRel("List of all categories"),
                linkTo(methodOn(CategoryController.class).getCategory(category.id())).withRel("Get Category by ID"),
                linkTo(methodOn(CategoryController.class).createCategory(null)).withRel("Create a new category")
        );
    }


    public EntityModel<CategoryIdDTO> toIdModel(CategoryIdDTO category) {
        return EntityModel.of(category,
                linkTo(methodOn(CategoryController.class).getCategory(category.id())).withRel("Get Category by ID")
        );
    }
}
