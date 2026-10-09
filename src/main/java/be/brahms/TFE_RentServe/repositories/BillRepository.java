package be.brahms.TFE_RentServe.repositories;

import be.brahms.TFE_RentServe.models.entities.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for managing Bill. Provide: Get sum earn the month
 *
 * @author Brahim K
 */
@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {}
