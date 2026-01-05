package be.brahms.TFE_RentServe.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * This class represents the link between a user and a favor.
 * It extends the BaseEntity class and includes relationships to pictures, rentals, and reviews.
 */
@Entity
@Table(name = "user_favour", schema = "liquibase_rentserve")
@Getter
@Setter
@AllArgsConstructor
public class UserFavor extends BaseEntity {
    /**
     * A description of the favor.
     * This is stored as text, allowing for longer descriptions.
     */
    @Column(name = "description_favor", columnDefinition = "TEXT")
    private String descriptionFavor;

    /**
     * The price per hour for the favor.
     * Stored as a decimal with a precision of 7 and a scale of 2.
     * This value cannot be null.
     */
    @Column(name = "price_hour_favor", nullable = false, precision = 7, scale = 2)
    private BigDecimal priceHourFavor;

    /**
     * This is a boolean to define,
     * the service is available or not
     */
    @Column(name = "isAvailable")
    private boolean isAvailable;

    // Relation ManyToOne
    /**
     * The user who owns or offers the favor.
     * This field cannot be null.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    /**
     * The favor associated with the user.
     * This field cannot be null.
     */
    @ManyToOne
    @JoinColumn(name = "favor_id", nullable = false)
    private Favor favor;

    // Constructor by default

    /**
     * Default constructor for UserFavor.
     */
    public UserFavor() {
    }

    // Relation OneToMany
    /**
     * A set of rentals involving this user-favor.
     * Allows tracking how the favor has been rented.
     */
    @OneToMany(mappedBy = "userFavor", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<Rental> rentals = new HashSet<>();

    /**
     * A set of reviews related to this user-favor.
     * Represents feedback left by other users.
     */
    @OneToMany(mappedBy = "userFavor", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<Review> reviews = new HashSet<>();

    // Relation ManyToMany
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "picture_user_favour",
            joinColumns = @JoinColumn(name = "user_favor_id"),
            inverseJoinColumns = @JoinColumn(name = "picture_id")
    )
    private Set<Picture> pictures = new HashSet<>();
}
