package be.brahms.TFE_RentServe.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * This class represents a rental and extends the BaseEntity class.
 * It includes details about the rental amount, dates, times, and its relationships to users, materials, favours, and bills.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@ToString
@Table(name = "rentals", schema = "liquibase_rentserve")
public class Rental extends BaseEntity {
    /**
     * The total amount for the rental.
     * Stored as a decimal with a precision of 7 and a scale of 2.
     */
    @Column(name = "amount", precision = 7, scale = 2)
    private BigDecimal amount;
    /**
     * The start date of the rental.
     * Represents the date when the rental begins.
     */
    @Column(name = "start_date_at")
    private LocalDate starDateAt;
    /**
     * The end date of the rental.
     * Represents the date when the rental ends.
     */
    @Column(name = "end_date_at")
    private LocalDate endDateAt;
    /**
     * The start time of the rental.
     * Represents the time when the rental begins on the start date.
     */
    @Column(name = "start_time")
    private LocalTime startTime;
    /**
     * The end time of the rental.
     * Represents the time when the rental ends on the end date.
     */
    @Column(name = "end_time")
    private LocalTime endTime;

    // Constructor by default

    /**
     * Default constructor for Rental.
     */
    public Rental() {
    }

    // Relation ManyToOne
    /**
     * The user who made the rental.
     * A rental is associated with one user.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * The user-material relationship associated with the rental.
     * A rental may involve a material that belongs to a user.
     */
    @ManyToOne
    @JoinColumn(name = "user_material_id")
    private UserMaterial userMaterial;

    /**
     * The user-favor relationship associated with the rental.
     * A rental may involve a favor that belongs to a user.
     */
    @ManyToOne
    @JoinColumn(name = "user_favor_id")
    private UserFavor userFavor;

    /**
     * The bill associated with this rental.
     * A rental can be linked to one bill.
     */
    @ManyToOne
    @JoinColumn(name = "bill_id")
    private Bill bill;
}
