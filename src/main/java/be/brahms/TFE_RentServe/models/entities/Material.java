package be.brahms.TFE_RentServe.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * This class represents a material (item or resource) and extends the BaseEntity class.
 * It includes the material's name, description, price, state, availability, and relationships to users and categories.
 */
@Entity
@Table(name = "materials", schema = "liquibase_rentserve")
@Getter
@Setter
@AllArgsConstructor
public class Material extends BaseEntity {
    /**
     * The name of the material.
     * This value cannot be null.
     */
    @Column(name = "name_material", nullable = false)
    private String nameMaterial;
    /**
     * A flag indicating whether the material is available or not.
     * The default value is false (unavailable) if not set.
     */
    @Column(name = "is_available")
    private boolean isAvailable;

    // Constructor by default

    /**
     * Default constructor for Material.
     */
    public Material() {
    }

    /**
     * The constructor for create a new material
     *
     * @param nameMaterial The name of the material
     * @param category     The name of the category
     * @param isAvailable  The boolean to check if is activated or not
     */
    public Material(String nameMaterial, String category, Boolean isAvailable) {
        this.nameMaterial = nameMaterial;
        this.category = new Category(category);
        this.isAvailable = isAvailable;
    }

    /**
     * A set of user-material relationships.
     * This represents which users have used this material.
     * A material can be linked to multiple users.
     */
    // Relation OneToMany
    @OneToMany(mappedBy = "material")
    private Set<UserMaterial> userMaterials = new HashSet<>();

    /**
     * The category to which this material belongs.
     * A material is associated with one category.
     */
    // Relation ManyToOne
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    /**
     * Returns a string representation of the Material object.
     * <p>
     * This includes only following fields:
     * - id
     * - name material
     * - description
     * - price per hour
     * - availability
     * </p>
     *
     * @return a tableau with data
     */
    @Override
    public String toString() {
        return "Material {" +
                "id= " + getId() + "\n " +
                "Name= " + nameMaterial + "\n " +
                "active= " + isAvailable + "\n " +
                "} ";
    }
}
