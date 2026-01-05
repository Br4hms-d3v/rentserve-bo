package be.brahms.TFE_RentServe.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * This class represents a review and extends the BaseEntity class.
 * It includes a comment, a rating, an active status, and relationships to user-material and user-favor.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@ToString
@Table(name = "reviews", schema = "liquibase_rentserve")
public class Review extends BaseEntity {
    /**
     * The text comment of the review.
     * Stored as text to allow longer feedback.
     */
    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;
    /**
     * The rating given in the review.
     * Stored as a Double value (e.g., 4.5).
     */
    @Column(name = "rating")
    private Double rating;
    /**
     * Indicates whether the review is active or not.
     * A Boolean value where true means active.
     */
    @Column(name = "is_active")
    private Boolean isActive;

    // Constructor by default

    /**
     * Default constructor for Review.
     */
    public Review() {
    }

    // Relation ManyToOne
    /**
     * The user-material relationship associated with this review.
     * A review may be linked to a user-material.
     */
    @ManyToOne
    @JoinColumn(name = "user_material_id")
    private UserMaterial userMaterial;

    /**
     * The user-favor relationship associated with this review.
     * A review may be linked to a user-favor.
     */
    @ManyToOne
    @JoinColumn(name = "user_Favor_id")
    private UserFavor userFavor;
}
