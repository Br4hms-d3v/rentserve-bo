package be.brahms.TFE_RentServe.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * This class represents a second residence and extends the BaseEntity class.
 * It includes address details and its relationship to a user.
 */
@Entity
@Table(name = "second_residences", schema = "liquibase_rentserve")
@Getter
@Setter
@AllArgsConstructor
@ToString
public class SecondResidence extends BaseEntity {

    /**
     * The street address of the second residence.
     * This value cannot be null.
     */
    @Column(name = "street", nullable = false)
    private String street;
    /**
     * The city where the second residence is located.
     * Maximum length is 100 characters and cannot be null.
     */
    @Column(name = "city", length = 100, nullable = false)
    private String city;
    /**
     * The ZIP code of the second residence.
     * Maximum length is 50 characters and cannot be null.
     */
    @Column(name = "zip_code", length = 50, nullable = false)
    private String zipCode;

    // Constructor by default

    /**
     * Default constructor for Seconde residence.
     */
    public SecondResidence() {
    }

    // Relation ManyToOne
    /**
     * The user who owns or is associated with this second residence.
     * A residence belongs to one user.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
