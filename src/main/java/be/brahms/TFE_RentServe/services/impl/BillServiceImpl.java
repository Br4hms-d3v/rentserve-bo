package be.brahms.TFE_RentServe.services.impl;

import be.brahms.TFE_RentServe.repositories.BillRepository;
import be.brahms.TFE_RentServe.services.BillService;
import org.springframework.stereotype.Service;

/**
 * Service implementation for managing Bill. Uses BillRepository to perform database operations uses
 * BillMapper to map between form to entity or dto to entity
 *
 * @author Brahim K
 */
@Service
public class BillServiceImpl implements BillService {

  private final BillRepository billRepository;

  /**
   * Constructor with params
   *
   * @param billRepository the billRepo to access bill data
   */
  public BillServiceImpl(BillRepository billRepository) {
    this.billRepository = billRepository;
  }
}
