package be.brahms.TFE_RentServe.repositories;

import be.brahms.TFE_RentServe.enums.Status;
import be.brahms.TFE_RentServe.models.entities.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for managing Bill. Provide: Get sum earn the month
 *
 * @author Brahim K
 */
@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

    Optional<Bill> findBillByUser_idAndStatus(long user_id, Status status);
}
