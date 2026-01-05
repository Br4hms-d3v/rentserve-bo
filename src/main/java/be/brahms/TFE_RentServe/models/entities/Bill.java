package be.brahms.TFE_RentServe.models.entities;

import be.brahms.TFE_RentServe.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Set;

/**
 * This class represents a bill and extends the BaseEntity class.
 * It includes information about the bill's amount, status, and payment state.
 * It also defines relationships with rentals and a user.
 */
@Entity
@Table(name = "bills", schema = "liquibase_rentserve")
@Getter
@Setter
@AllArgsConstructor
@ToString
public class Bill extends BaseEntity {
    /**
     * The total amount of the bill.
     * This is a decimal value with a precision of 7 and a scale of 2.
     * It cannot be null.
     */
    @Column(name = "amount", precision = 7, scale = 2, nullable = false)
    private BigDecimal amount;
    /**
     * The status of the bill (e.g., PENDING, PAID, etc.).
     * Stored as a string to match the Status enum.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
    /**
     * Indicates whether the bill has been paid or not.
     * A Boolean value where true means paid and false means not paid.
     */
    @Column(name = "is_paid")
    private Boolean isPaid;

    // Constructor by default

    /**
     * Default constructor for Bill.
     */
    public Bill() {
    }

    /**
     * A set of rentals associated with this bill.
     * A bill can be related to multiple rentals.
     * This relationship uses cascade for merge and persist operations.
     */
    // Relation OneToMany
    @OneToMany(mappedBy = "bill", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<Rental> rentals;

    /**
     * The user who is associated with this bill.
     * A bill is related to one user.
     */
    // Relation ManyToOne
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
