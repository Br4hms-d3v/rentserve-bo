package be.brahms.TFE_RentServe.controller;

import be.brahms.TFE_RentServe.hateoas.favor.FavorAssembler;
import be.brahms.TFE_RentServe.hateoas.favor.FavorByIdAssembler;
import be.brahms.TFE_RentServe.models.dtos.favor.FavorByIdDTO;
import be.brahms.TFE_RentServe.models.dtos.favor.FavorDTO;
import be.brahms.TFE_RentServe.models.forms.favor.FavorFormDTO;
import be.brahms.TFE_RentServe.models.forms.favor.UpdateFavorFormDTO;
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

    /**
     * This constructor is used to inject the necessary service for handling favor-related request.
     *
     * @param favorService       the service used for favor management
     * @param favorAssembler     the assembler used to convert Favor object to into FavorDto models
     * @param favorByIdAssembler the assembler used to convert Favor object to into FavorByIdDto models
     */
    public FavorController(FavorService favorService, FavorAssembler favorAssembler, FavorByIdAssembler favorByIdAssembler) {
        this.favorService = favorService;
        this.favorAssembler = favorAssembler;
        this.favorByIdAssembler = favorByIdAssembler;
    }

    /**
     * Get a list of favour
     *
     * @return a list of all favour
     */
    @GetMapping("list")
    @PreAuthorize("hasAnyRole('MEMBER','MODERATOR','ADMIN')")
    public ResponseEntity<CollectionModel<FavorDTO>> getAllFavors() {
        List<FavorDTO> favour = favorService.findAllFavour();
        return ResponseEntity.ok(CollectionModel.of(favour));
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
        FavorByIdDTO favorId = favorService.findFavourById(id);
        EntityModel<FavorByIdDTO> modelFavorId = favorByIdAssembler.toModel(favorId);
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
        FavorDTO favor = favorService.createFavor(form);
        EntityModel<FavorDTO> modelFavor = favorAssembler.toModel(favor);
        return ResponseEntity.ok().body(modelFavor);
    }

    /**
     * Get a list of favour grouped by name of category
     *
     * @param nameCategory name of category
     * @return a list of favour grouped by name of category
     */
    @GetMapping("category/{nameCategory}")
    @PreAuthorize("hasAnyRole('MEMBER','MODERATOR','ADMIN')")
    public ResponseEntity<CollectionModel<FavorDTO>> getAllFavourByNameCategory(@PathVariable String nameCategory) {
        List<FavorDTO> favour = favorService.findFavourByCategoryName(nameCategory);
        CollectionModel<FavorDTO> favourToCollectionModel = favorAssembler.toCollectionModel(favour);
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
    public ResponseEntity<String> deleteFavor(@PathVariable long id) {
        favorService.deleteFavor(id);
        return ResponseEntity.ok().body("The favor has been deleted");
    }

}
