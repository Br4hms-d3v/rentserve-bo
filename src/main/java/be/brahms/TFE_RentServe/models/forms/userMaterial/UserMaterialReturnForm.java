package be.brahms.TFE_RentServe.models.forms.userMaterial;

import be.brahms.TFE_RentServe.enums.State;

/**
 * Form representing the information required to return a material to its owner.
 *
 * @param id the unique identifier of the material
 * @param available indicates whether the material is available after the return
 * @param state the state of the material after the return
 */
public record UserMaterialReturnForm(Long id, Boolean available, State state) {}
