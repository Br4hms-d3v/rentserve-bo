package be.brahms.TFE_RentServe.models.entities;

import be.brahms.TFE_RentServe.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashSet;
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
public class User extends BaseEntity {


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

    /**
     * This constructor makes a new user.
     * You give name, first name, birthdate, pseudo, password, role,
     * street, city, zip code, and if the user is active.
     *
     * @param name      the last name
     * @param firstName the first name
     * @param birthdate the birthdate
     * @param pseudo    the username
     * @param email     the email
     * @param password  the password
     * @param street    the street name
     * @param city      the city
     * @param zipCode   the postal code
     */
    public User(String name, String firstName, LocalDate birthdate, String pseudo, String email, String password, String street, String city, String zipCode) {
        this();
        this.name = name;
        this.firstName = firstName;
        this.birthdate = birthdate;
        this.pseudo = pseudo;
        this.email = email;
        this.password = password;
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
    }

    /**
     * This constructor is a log-in for a user who as an account
     * Introduce in the form an email or pseudo with password
     *
     * @param email    the email
     * @param pseudo   the pseudo
     * @param password the password
     */
    public User(String email, String pseudo, String password) {
        this();
        this.email = email;
        this.pseudo = pseudo;
        this.password = password;
    }

    /**
     * Creates a new user with form data.
     *
     * @param name      the last name
     * @param firstName the first name
     * @param birthdate the birth date
     * @param pseudo    the username
     * @param email     the email address
     * @param street    the street name
     * @param city      the city
     * @param zipCode   the postal code
     */
    public User(String name, String firstName, LocalDate birthdate, String pseudo, String email, String street, String city, String zipCode) {
        this();
        this.name = name;
        this.firstName = firstName;
        this.birthdate = birthdate;
        this.pseudo = pseudo;
        this.email = email;
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
    }

    /**
     * Update password for the user using form.
     *
     * @param email    the email address
     * @param password the password
     */
    public User(String email, String password) {
        this();
        this.email = email;
        this.password = password;
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

    // From UserDetails AND It's need it

    /**
     * This method gives the user roles.
     * Now, it gives nothing (empty list).
     */
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of();
//    }

//    /**
//     * This method gives the user's name (username).
//     * It returns the pseudo.
//     */
//    @Override
//    public String getUsername() {
//        return pseudo;
//    }
}
