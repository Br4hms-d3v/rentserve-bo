package be.brahms.TFE_RentServe.hateoas.favor;

import be.brahms.TFE_RentServe.controller.FavorController;
import be.brahms.TFE_RentServe.models.dtos.favor.FavorByIdDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class FavorByIdAssembler implements RepresentationModelAssembler<FavorByIdDTO, EntityModel<FavorByIdDTO>> {
    @Override
    public EntityModel<FavorByIdDTO> toModel(FavorByIdDTO favor) {
        return EntityModel.of(favor,
                linkTo(methodOn(FavorController.class).getFavorById(favor.id())).withRel("Get detail by id: " + favor.id()));
    }

}
