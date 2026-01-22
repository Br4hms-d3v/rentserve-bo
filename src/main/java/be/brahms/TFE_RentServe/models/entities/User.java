package be.brahms.TFE_RentServe.models.entities;

import be.brahms.TFE_RentServe.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class represents a user and extends the BaseEntity class.
 * It includes personal information, address, role, active status,
 * and relationships to second residences, materials, favours, and rentals.
 */
@Entity
@Table(name = "users", schema = "liquibase_rentserve")
@Getter
@Setter
@ToString
public class User extends BaseEntity implements UserDetails {


    /**
     * The last name of the user.
     * Maximum length is 200 characters and cannot be null.
     */
    @Column(name = "name", length = 200, nullable = false)
    private String name;
    /**
     * The first name of the user.
     * Maximum length is 150 characters and cannot be null.
     */
    @Column(name = "first_name", length = 150, nullable = false)
    private String firstName;
    /**
     * The birthdate of the user.
     * Cannot be null.
     */
    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;
    /**
     * The pseudonym or username of the user.
     * Cannot be null.
     * Must be unique
     */
    @Column(name = "pseudo", unique = true, nullable = false)
    private String pseudo;
    /**
     * The email of the user.
     * Must be unique and cannot be null.
     */
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    /**
     * The password of the user.
     * Cannot be null.
     */
    @Column(name = "password", nullable = false)
    private String password;
    /**
     * The role of the user (e.g., MEMBER, MODERATOR, ADMIN).
     * Stored as a string and cannot be null.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;
    /**
     * The street address of the user.
     * Cannot be null.
     */
    @Column(name = "street", nullable = false)
    private String street;
    /**
     * The city where the user lives.
     * Maximum length is 100 characters and cannot be null.
     */
    @Column(name = "city", length = 100, nullable = false)
    private String city;
    /**
     * The zipcode of the user's address.
     * Maximum length is 60 characters and cannot be null.
     */
    @Column(name = "zip_code", length = 60, nullable = false)
    private String zipCode;
    /**
     * Indicates if the user account is active.
     * Default value is false.
     */
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = false;

    // Constructor by default

    /**
     * Default constructor for User.
     */
    public User() {
    }

    // OneToMany
    /**
     * A set of second residences linked to the user.
     * A user can have multiple second residences.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE)
    private Set<SecondResidence> secondResidences;

    /**
     * A set of user-material relationships.
     * Represents materials owned or used by the user.
     */
    @OneToMany(mappedBy = "user")
    private Set<UserMaterial> userMaterials = new HashSet<>();

    /**
     * A set of user-favor relationships.
     * Represents favours owned or used by the user.
     */
    @OneToMany(mappedBy = "user")
    private Set<UserFavor> userFavours = new HashSet<>();

    /**
     * A set of rentals made by the user.
     */
    @OneToMany(mappedBy = "user")
    private Set<Rental> rentals = new HashSet<>();

    /**
     * A set of reviews related to this user.
     * Represents list review from user.
     */
    @OneToMany(mappedBy = "user", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<Review> reviews = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return pseudo;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
