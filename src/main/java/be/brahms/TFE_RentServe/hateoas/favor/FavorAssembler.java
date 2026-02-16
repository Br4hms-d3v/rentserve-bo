package be.brahms.TFE_RentServe.hateoas.favor;

import be.brahms.TFE_RentServe.models.dtos.favor.FavorDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

/**
 * This assembler adds useful navigation links related to favor
 * operations such as retrieving
 *
 */
@Component
public class FavorAssembler implements RepresentationModelAssembler<FavorDTO, EntityModel<FavorDTO>> {

    @Override
    public EntityModel<FavorDTO> toModel(FavorDTO entity) {
        return null;
    }
}
