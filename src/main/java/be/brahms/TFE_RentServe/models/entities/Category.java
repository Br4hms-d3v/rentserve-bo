package be.brahms.TFE_RentServe.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

/**
 * This class represents a category of items and extends the BaseEntity class.
 * It includes a name for the category and relationships to favours and materials.
 */
@Entity
@Table(name = "categories", schema = "liquibase_rentserve")
@Getter
@Setter
@AllArgsConstructor
@ToString
public class Category extends BaseEntity {
    /**
     * The name of the category.
     * This value must be unique, cannot be null, and has a maximum length of 150 characters.
     */
    @Column(name = "name_category", length = 150, unique = true, nullable = false)
    private String nameCategory;

    // Constructor by default

    /**
     * Default constructor for Category.
     */
    public Category() {
    }

    /**
     * Constructor with params
     * for the form Category
     *
     * @param nameCategory the new name of category
     */
    public Category(String nameCategory) {
        this();
        this.nameCategory = nameCategory;
    }

    /**
     * A set of favours associated with this category.
     * A category can have multiple favours.
     * The relationship is maintained through cascading persist and merge operations.
     */
    // Relation OneToMany
    @OneToMany(mappedBy = "category", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Favor> favours;

    /**
     * A set of materials associated with this category.
     * A category can have multiple materials.
     * The relationship is maintained through cascading persist and merge operations.
     */
    @OneToMany(mappedBy = "category", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Material> materials;
}
