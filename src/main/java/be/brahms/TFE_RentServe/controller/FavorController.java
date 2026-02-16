package be.brahms.TFE_RentServe.controller;

import be.brahms.TFE_RentServe.hateoas.favor.FavorAssembler;
import be.brahms.TFE_RentServe.mappers.FavorMapper;
import be.brahms.TFE_RentServe.models.dtos.favor.FavorDTO;
import be.brahms.TFE_RentServe.models.entities.Favor;
import be.brahms.TFE_RentServe.services.FavorService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * This controller manages Favor
 * It has a method to display a list of favour
 */
@RestController
@RequestMapping("/api/favor/")
public class FavorController {

    private final FavorService favorService;
    private final FavorAssembler favorAssembler;
    private final FavorMapper favorMapper;

    public FavorController(FavorService favorService, FavorAssembler favorAssembler, FavorMapper favorMapper) {
        this.favorService = favorService;
        this.favorAssembler = favorAssembler;
        this.favorMapper = favorMapper;
    }

    @GetMapping("list")
    public ResponseEntity<CollectionModel<FavorDTO>> getAllFavors() {
        List<Favor> favour = favorService.findAllFavour();
        List<FavorDTO> favourDto = favour
                .stream()
                .map(favorMapper::toDto)
                .toList();
        return ResponseEntity.ok(CollectionModel.of(favourDto));
    }

}
