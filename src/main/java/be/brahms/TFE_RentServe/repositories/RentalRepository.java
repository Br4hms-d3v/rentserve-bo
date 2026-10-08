package be.brahms.TFE_RentServe.repositories;

import be.brahms.TFE_RentServe.models.entities.Rental;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository for managing Rental. Provide: Get sum earn the month
 *
 * @author Brahim K
 */
@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {

  /**
   * Get total earn from the first day util last day (the same month) COALESCE mean to replace a
   * NULL value whit another value
   *
   * @param userId the owner identifier
   * @param dateStart the date start
   * @param dateEnd the end date
   * @return a total amount earn the month
   */
  @Query(
      "SELECT COALESCE(SUM (r.amount),0) FROM Rental r LEFT JOIN r.userFavor uf LEFT JOIN r.userMaterial um JOIN r.bill b WHERE (uf.user.id = :userId OR um.user.id = :userId) AND b.isPaid = true AND r.createdAt >= :dateStart AND r.createdAt <= :dateEnd")
  BigDecimal totalEarned(
      @Param("userId") Long userId,
      @Param("dateStart") LocalDate dateStart,
      @Param("dateEnd") LocalDate dateEnd);
}
