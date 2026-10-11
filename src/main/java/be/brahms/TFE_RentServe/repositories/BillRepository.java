package be.brahms.TFE_RentServe.repositories;

import be.brahms.TFE_RentServe.enums.Status;
import be.brahms.TFE_RentServe.models.entities.Bill;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository for managing Bill. Provide: Get sum earn the month
 *
 * @author Brahim K
 */
@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

  /**
   * Fin a bill not paid yet
   *
   * @param user_id the identifier of user
   * @param status the status is pending
   * @return a bill who is not paid yet
   */
  Optional<Bill> findBillByUser_idAndStatus(long user_id, Status status);

  /**
   * Get a list of bills from user id
   *
   * @param userId the identifier user
   * @return a list of bills
   */
  @Query("SELECT b from Bill b WHERE b.user.id = :userId")
  List<Bill> findBillsByUserId(long userId);
}
