package be.brahms.TFE_RentServe.models.dtos.review;

/**
 * A Dto (Data transfer Object) for review. It contains data about the review like comment and
 * rating
 *
 * @param id the identifier review
 * @param comment the comment
 * @param rating the evaluates with a note on the favor or material
 * @param isActive The review is available
 */
public record ReviewDTO(Long id, String comment, Double rating, Boolean isActive) {}
