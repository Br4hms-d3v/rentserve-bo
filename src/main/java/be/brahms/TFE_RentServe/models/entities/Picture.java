package be.brahms.TFE_RentServe.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * This class represents a picture and extends the BaseEntity class.
 * It includes the picture's name and its relationships to user-material and user-favor.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "pictures", schema = "liquibase_rentserve")
public class Picture extends BaseEntity {
    /**
     * The name of the picture.
     * This value cannot be null.
     */
    @Column(name = "name_picture", nullable = false)
    private String namePicture;

    // Constructor by default

    /**
     * Default constructor for Picture.
     */
    public Picture() {
    }

    // Relation Many To Many
    /**
     * The user-material relationship to which this picture belongs.
     * A picture is associated with one user-material.
     */
    @ManyToMany(mappedBy = "pictures")
    private List<UserMaterial> userMaterial;

    /**
     * The user-favor relationship to which this picture belongs.
     * A picture is associated with one user-favor.
     */
    @ManyToMany(mappedBy = "pictures")
    private List<UserFavor> userFavor;
}
