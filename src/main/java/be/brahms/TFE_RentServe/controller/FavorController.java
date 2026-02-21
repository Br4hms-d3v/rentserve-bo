package be.brahms.TFE_RentServe.controller;

import be.brahms.TFE_RentServe.hateoas.favor.FavorAssembler;
import be.brahms.TFE_RentServe.hateoas.favor.FavorByIdAssembler;
import be.brahms.TFE_RentServe.mappers.FavorMapper;
import be.brahms.TFE_RentServe.models.dtos.favor.FavorByIdDTO;
import be.brahms.TFE_RentServe.models.dtos.favor.FavorDTO;
import be.brahms.TFE_RentServe.models.dtos.favor.FavorFormDTO;
import be.brahms.TFE_RentServe.models.dtos.favor.UpdateFavorFormDTO;
import be.brahms.TFE_RentServe.models.entities.Favor;
import be.brahms.TFE_RentServe.services.FavorService;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This controller manages Favor
 * It has a method to display a list of favour
 * It has a method to display a details of favor
 * It has a method to create a new favor
 * It has a method to display a list of favour from name of category
 * It has a method to edit a favor
 * It has a method to delete a favor
 *
 * @author Brahim k
 */
@RestController
@RequestMapping("/api/favor")
public class FavorController {

    private final FavorService favorService;
    private final FavorAssembler favorAssembler;
    private final FavorByIdAssembler favorByIdAssembler;
    private final FavorMapper favorMapper;

    /**
     * This constructor is used to inject the necessary service for handling favor-related request.
     *
     * @param favorService       the service used for favor management
     * @param favorAssembler     the assembler used to convert Favor object to into FavorDto models
     * @param favorByIdAssembler the assembler used to convert Favor object to into FavorByIdDto models
     * @param favorMapper        hateoas create the link to redirect to endPoints
     */
    public FavorController(FavorService favorService, FavorAssembler favorAssembler, FavorByIdAssembler favorByIdAssembler, FavorMapper favorMapper) {
        this.favorService = favorService;
        this.favorAssembler = favorAssembler;
        this.favorByIdAssembler = favorByIdAssembler;
        this.favorMapper = favorMapper;
    }

    /**
     * Get a list of favour
     *
     * @return a list of all favour
     */
    @GetMapping("list")
    @PreAuthorize("hasAnyRole('MEMBER','MODERATOR','ADMIN')")
    public ResponseEntity<CollectionModel<FavorDTO>> getAllFavors() {
        List<Favor> favour = favorService.findAllFavour();
        List<FavorDTO> favourDto = favour
                .stream()
                .map(favorMapper::toDto)
                .toList();
        return ResponseEntity.ok(CollectionModel.of(favourDto));
    }

    /**
     * Get favor's data by his ID
     *
     * @param id identifier unique
     * @return data's about the favor
     */
    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('MEMBER','MODERATOR','ADMIN')")
    public ResponseEntity<EntityModel<FavorByIdDTO>> getFavorById(@PathVariable Long id) {
        Favor favorId = favorService.findFavourById(id);
        FavorByIdDTO favorByIdDTO = favorMapper.toDtoById(favorId);
        EntityModel<FavorByIdDTO> modelFavorId = favorByIdAssembler.toModel(favorByIdDTO);
        return ResponseEntity.ok(modelFavorId);
    }

    /**
     * Create a new favor
     *
     * @param form the form to create a new favor
     * @return a new favor
     */
    @PostMapping("new")
    @PreAuthorize("hasAnyRole('MODERATOR','ADMIN')")
    public ResponseEntity<EntityModel<FavorDTO>> createFavor(@RequestBody @Valid FavorFormDTO form) {
        Favor favor = favorService.createFavor(form);
        FavorDTO favorDTO = favorMapper.toDto(favor);
        return ResponseEntity.ok().body(favorAssembler.toModel(favorDTO));
    }

    @GetMapping("category/{nameCategory}")
    @PreAuthorize("hasAnyRole('MEMBER','MODERATOR','ADMIN')")
    public ResponseEntity<CollectionModel<FavorDTO>> getAllFavourByNameCategory(@PathVariable String nameCategory) {
        List<Favor> favour = favorService.findFavourByCategoryName(nameCategory);
        List<FavorDTO> favourDto = favorMapper.toListDto(favour);
        CollectionModel<FavorDTO> favourToCollectionModel = favorAssembler.toCollectionModel(favourDto);
        return ResponseEntity.ok().body(favourToCollectionModel);
    }

    /**
     * This method update some data's about the favor
     * Check if the name of favor already exist
     *
     * @param id   the identifier of favor
     * @param form the form with new data to update
     * @return a new response with data updated
     */
    @PutMapping("edit/{id}")
    @PreAuthorize("hasAnyRole('MODERATOR','ADMIN')")
    public ResponseEntity<EntityModel<FavorDTO>> updateFavor(@PathVariable Long id, @RequestBody @Valid UpdateFavorFormDTO form) {
        FavorDTO favorDto = favorService.updateFavor(id, form);
        EntityModel<FavorDTO> modelFavor = favorAssembler.toModel(favorDto);
        return ResponseEntity.ok().body(modelFavor);
    }
    /**
     * This method to delete
     *
     * @param id the identifier
     * @return a message to confirm deleting
     */
    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteFavor(@PathVariable Long id) {
        favorService.deleteFavor(id);
        return ResponseEntity.ok().body("The favor has been deleted");
    }

}
