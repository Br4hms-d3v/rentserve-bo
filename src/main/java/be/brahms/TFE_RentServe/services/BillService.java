package be.brahms.TFE_RentServe.services;

import be.brahms.TFE_RentServe.models.dtos.bill.BillDTO;

/** Service interface for managing rental. Defines business operations related to rental entity */
public interface BillService {

  /**
   * This method get a list of user favor by ID
   *
   * @param id the identifier of user favor
   * @return a detail about the user favor
   */
  BillDTO findById(long id);
}
