package be.brahms.TFE_RentServe.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * This is a base entity class that includes common fields for all entities.
 * It provides automatic management of creation and update timestamps.
 */
@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

    /**
     * The unique identifier of the entity.
     * Automatically generated when a new entity is created.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    /**
     * The date and time when the entity was created.
     * Cannot be updated after creation.
     */
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDate createdAt;
    /**
     * The date and time when the entity was last updated.
     * This field is updated automatically on every update.
     */
    @Column(name = "updated_at")
    private LocalDate updatedAt;

    /**
     * This method is called before the entity is persisted (saved for the first time).
     * It sets the created timestamp to the current time.
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDate.now();
    }

    /**
     * This method is called before the entity is updated.
     * It sets the updated timestamp to the current time.
     */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDate.now();
    }

    // Constructor by default

    /**
     * Default constructor for BaseEntity.
     */
    public BaseEntity() {
    }
}
