package be.brahms.TFE_RentServe.repositories;

import be.brahms.TFE_RentServe.models.entities.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill, Long> {
}
