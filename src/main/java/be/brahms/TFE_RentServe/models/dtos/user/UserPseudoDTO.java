package be.brahms.TFE_RentServe.models.dtos.user;

/**
 * UserPseudo is a data transfer object for user take only the pseudo name
 *
 * @param id the identifier of user
 * @param pseudo the pseudo of user
 */
public record UserPseudoDTO(Long id, String pseudo) {}
