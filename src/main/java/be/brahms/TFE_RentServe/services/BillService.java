package be.brahms.TFE_RentServe.services;

import be.brahms.TFE_RentServe.models.dtos.bill.BillDTO;
import java.util.List;

/** Service interface for managing rental. Defines business operations related to rental entity */
public interface BillService {

  /**
   * This method get a list of user favor by ID
   *
   * @param id the identifier of user favor
   * @return a detail about the user favor
   */
  BillDTO findById(long id);

  /**
   * This method get a list bills of user
   *
   * @param userId the identifier
   * @return a list of bill
   */
  List<BillDTO> findAllByUserId(long userId);
}
